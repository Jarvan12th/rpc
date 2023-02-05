package com.example.rpc.serializer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JSONEncoderTest {

    @Test
    public void encode() {
        TestBean testBean = new TestBean("name", 18);

        JSONEncoder encoder = new JSONEncoder();
        byte[] result = encoder.encode(testBean);

        assertArrayEquals(new byte[] {123, 34, 97, 103, 101, 34, 58, 49, 56, 44, 34, 110, 97, 109, 101, 34, 58, 34, 110, 97, 109, 101, 34, 125}, result);
    }
}