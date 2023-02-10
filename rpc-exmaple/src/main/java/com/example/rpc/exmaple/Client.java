package com.example.rpc.exmaple;

import com.example.rpc.client.RpcClient;

public class Client {

    public static void main(String[] args) throws Exception {
        RpcClient client = new RpcClient();
        CalculateService service = client.getProxy(CalculateService.class);

        int m = service.add(2, 3);
        System.out.println("add result: " + m);

        int n = service.subtract(10, 4);
        System.out.println("subtract result: " + n);
    }
}
