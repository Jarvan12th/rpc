package com.example.rpc.server;

import com.example.rpc.common.utils.ReflectionUtils;
import com.example.rpc.protocol.Request;
import com.example.rpc.protocol.ServiceDescriptor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ServiceManagerTest {

    ServiceManager serviceManager = new ServiceManager();

    @Test
    public void register() {
        TestService bean = new TestServiceImpl();

        serviceManager.register(TestService.class, bean);

        assertEquals("[clazz=com.example.rpc.server.TestService,method=hello,returnType=void,parametersType=[]]",
                serviceManager.getServices().keySet().toString());
    }

    @Test
    public void lookUp() {
        Request request = new Request();
        ServiceDescriptor serviceDescriptor = ServiceDescriptor.from(TestService.class, ReflectionUtils.getPublicMethods(TestServiceImpl.class)[0]);
        request.setService(serviceDescriptor);
        ServiceInstance serviceInstance = serviceManager.lookUp(request);

        assertNull(serviceInstance);

        serviceManager.register(TestService.class, new TestServiceImpl());
        serviceInstance = serviceManager.lookUp(request);

        assertNotNull(serviceInstance);
    }
}