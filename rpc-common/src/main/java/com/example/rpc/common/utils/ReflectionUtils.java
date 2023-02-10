package com.example.rpc.common.utils;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;

public class ReflectionUtils {
    public static <T> T newInstance(Class<T> clazz) throws Exception {
        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new Exception("new instance exception");
        }
    }

    public static Method[] getPublicMethods(Class clazz) {
        Method[] methods = clazz.getDeclaredMethods();

        return Arrays.stream(methods).filter(m -> Modifier.isPublic(m.getModifiers())).toArray(Method[]::new);
    }

    public static Object invoke(Object object, Method method, Object... args) throws Exception {
        try {
            return method.invoke(object, args);
        } catch (Exception e) {
            throw new Exception("invoke method exception");
        }
    }
}
