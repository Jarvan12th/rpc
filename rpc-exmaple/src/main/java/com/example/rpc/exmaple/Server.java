package com.example.rpc.exmaple;

import com.example.rpc.server.RpcServer;
import com.example.rpc.server.RpcServerConfig;

public class Server {

    public static void main(String[] args) throws Exception {
        RpcServer server = new RpcServer(new RpcServerConfig());

        server.register(CalculateService.class, new CalculateServiceImpl());
        server.start();
    }
}
