package com.example.rpc.serializer;

public interface Decoder {
    <T> T decode(byte[] bytes, Class<T> clazz);
}
