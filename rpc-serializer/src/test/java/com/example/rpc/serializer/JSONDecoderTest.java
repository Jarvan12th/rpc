package com.example.rpc.serializer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JSONDecoderTest {

    @Test
    public void decode() {
        JSONDecoder decoder = new JSONDecoder();
        TestBean result = decoder.decode(new byte[] {123, 34, 97, 103, 101, 34, 58, 49, 56, 44, 34, 110, 97, 109, 101, 34, 58, 34, 110, 97, 109, 101, 34, 125}, TestBean.class);

        assertEquals("name", result.getName());
        assertEquals(18, result.getAge());
    }
}