package com.example.rpc.protocol;

import lombok.Data;

@Data
public class Response {

    /**
     * 0:SUCCESS, !0:FAIL
     */
    private int code = 0;

    private String message = "SUCCESS";

    private Object data;
}
