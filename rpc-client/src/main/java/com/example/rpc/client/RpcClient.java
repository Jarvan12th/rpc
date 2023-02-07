package com.example.rpc.client;

import com.example.rpc.common.utils.ReflectionUtils;
import com.example.rpc.serializer.Decoder;
import com.example.rpc.serializer.Encoder;

import java.lang.reflect.Proxy;

public class RpcClient {

    private RpcClientConfig rpcClientConfig;

    private Encoder encoder;

    private Decoder decoder;

    private TransportSelector selector;

    public RpcClient() throws Exception {
        this(new RpcClientConfig());
    }

    public RpcClient(RpcClientConfig rpcClientConfig) throws Exception {
        this.rpcClientConfig = rpcClientConfig;

        this.encoder = ReflectionUtils.newInstance(rpcClientConfig.getEncoderClass());

        this.decoder = ReflectionUtils.newInstance(rpcClientConfig.getDecoderClass());

        this.selector = ReflectionUtils.newInstance(rpcClientConfig.getSelectorClass());
        this.selector.init(this.rpcClientConfig.getPeers(), this.rpcClientConfig.getConnectCount(), this.rpcClientConfig.getTransportClientClass());
    }

    public <T> T getProxy(Class<T> clazz) {
        return (T) Proxy.newProxyInstance(getClass().getClassLoader(), new Class[]{clazz}, new RemoteInvoker(clazz, encoder, decoder, selector));
    }
}
