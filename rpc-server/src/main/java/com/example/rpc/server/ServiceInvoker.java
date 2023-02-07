package com.example.rpc.server;

import com.example.rpc.common.utils.ReflectionUtils;
import com.example.rpc.protocol.Request;

public class ServiceInvoker {

    public Object invoke(ServiceInstance serviceInstance, Request request) throws Exception {
        return ReflectionUtils.invoke(serviceInstance.getTarget(), serviceInstance.getMethod(), request.getParameters());
    }
}
