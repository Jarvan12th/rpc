package com.example.rpc.transport;

import java.io.OutputStream;

public interface TransportServer {

    void init(int port, RequestHandler handler);

    void start();

    void stop();
}
