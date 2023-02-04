package com.example.rpc.common.utils;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ReflectionUtilsTest {

    @SneakyThrows
    @Test
    public void newInstance() {
        TestClass testClass = ReflectionUtils.newInstance(TestClass.class);

        assertNotNull(testClass);
    }

    @Test
    public void getPublicMethods() {
        Method[] result = ReflectionUtils.getPublicMethods(TestClass.class);

        assertEquals(1, result.length);
        assertEquals("b", result[0].getName());
    }

    @SneakyThrows
    @Test
    public void invoke() {
        Method method = ReflectionUtils.getPublicMethods(TestClass.class)[0];
        Object result = ReflectionUtils.invoke(new TestClass(), method);

        assertEquals("b", result);
    }

}