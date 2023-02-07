package com.example.rpc.server;

import com.example.rpc.common.utils.ReflectionUtils;
import com.example.rpc.protocol.Request;
import com.example.rpc.protocol.Response;
import com.example.rpc.serializer.Decoder;
import com.example.rpc.serializer.Encoder;
import com.example.rpc.transport.RequestHandler;
import com.example.rpc.transport.TransportServer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.io.OutputStream;

@Slf4j
public class RpcServer {

    private RpcServerConfig rpcServerConfig;

    private TransportServer transportServer;

    private Encoder encoder;

    private Decoder decoder;

    private ServiceManager serviceManager;

    private ServiceInvoker serviceInvoker;

    private RequestHandler handler = new RequestHandler() {
        @Override
        public void onRequest(InputStream receive, OutputStream toResponse) throws Exception {
            Response response = new Response();
            try {
                byte[] inputBytes = IOUtils.readFully(receive, receive.available());

                Request request = decoder.decode(inputBytes, Request.class);
                log.info("get request: {}", request);

                ServiceInstance serviceInstance = serviceManager.lookUp(request);
                Object result = serviceInvoker.invoke(serviceInstance, request);
                response.setData(result);
            } catch (Exception e) {
                log.warn(e.getMessage(), e);
                response.setCode(1);
                response.setMessage("RpcServer get error: " + e.getClass().getName() + ": " + e.getMessage());
            } finally {
                byte[] outputBytes = encoder.encode(response);
                toResponse.write(outputBytes);
                log.info("get response: {}", response);
            }
        }
    };

    public RpcServer(RpcServerConfig rpcServerConfig) throws Exception {
        this.rpcServerConfig = rpcServerConfig;

        this.transportServer = ReflectionUtils.newInstance(rpcServerConfig.getTransportClass());
        this.transportServer.init(rpcServerConfig.getPort(), this.handler);

        this.encoder = ReflectionUtils.newInstance(rpcServerConfig.getEncoderClass());

        this.decoder = ReflectionUtils.newInstance(rpcServerConfig.getDecoderClass());

        this.serviceManager = new ServiceManager();

        this.serviceInvoker = new ServiceInvoker();
    }

    public <T> void register(Class<T> interfaceClazz, T bean) {
        this.serviceManager.register(interfaceClazz, bean);
    }

    public void start() {
        this.transportServer.start();
    }

    public void stop() {
        this.transportServer.stop();
    }
}
