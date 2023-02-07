package com.example.rpc.server;

import com.example.rpc.common.utils.ReflectionUtils;
import com.example.rpc.protocol.Request;
import com.example.rpc.protocol.ServiceDescriptor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
@Slf4j
public class ServiceManager {

    private Map<ServiceDescriptor, ServiceInstance> services;

    public ServiceManager() {
        this.services = new ConcurrentHashMap<>();
    }

    public <T> void register(Class<T> interfaceClazz, T bean) {
        Method[] methods = ReflectionUtils.getPublicMethods(interfaceClazz);
        Arrays.stream(methods).forEach(m -> registryService(interfaceClazz, bean, m));
        log.info("registry services: {}", services);
    }

    public ServiceInstance lookUp(Request request) {
        return services.get(request.getService());
    }

    private <T> void registryService(Class<T> interfaceClazz, T bean, Method method) {
        services.put(ServiceDescriptor.from(interfaceClazz, method), new ServiceInstance(bean, method));
    }
}
