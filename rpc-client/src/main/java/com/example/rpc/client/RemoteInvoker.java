package com.example.rpc.client;

import com.example.rpc.protocol.Request;
import com.example.rpc.protocol.Response;
import com.example.rpc.protocol.ServiceDescriptor;
import com.example.rpc.serializer.Decoder;
import com.example.rpc.serializer.Encoder;
import com.example.rpc.transport.TransportClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

@Slf4j
public class RemoteInvoker implements InvocationHandler {

    private RpcClientConfig rpcClientConfig;

    private Class clazz;

    private Encoder encoder;

    private Decoder decoder;

    private TransportSelector selector;

    public RemoteInvoker(Class clazz, Encoder encoder, Decoder decoder, TransportSelector selector) {
        this.clazz = clazz;
        this.encoder = encoder;
        this.decoder = decoder;
        this.selector = selector;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Request request = new Request();
        request.setService(ServiceDescriptor.from(clazz, method));
        request.setParameters(args);

        Response response = invokeRemote(request);
        if (response == null || response.getCode() != 0) {
            throw new IllegalStateException("fail to invoke remote: " + response);
        }

        return response.getData();
    }

    private Response invokeRemote(Request request) {
        TransportClient client = null;
        Response response = null;

        try {
            client = selector.select();

            byte[] outputBytes = encoder.encode(request);
            InputStream inputStream = client.write(new ByteArrayInputStream(outputBytes));

            byte[] inputBytes = IOUtils.readFully(inputStream, inputStream.available());
            response = decoder.decode(inputBytes, Response.class);
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
            response.setCode(1);
            response.setMessage("RpcClient get error: " + e.getClass().getName() + ": " + e.getMessage());
        } finally {
            if (client != null) {
                selector.release(client);
            }
        }

        return response;
    }
}
