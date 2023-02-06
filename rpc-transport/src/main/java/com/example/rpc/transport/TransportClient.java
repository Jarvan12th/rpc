package com.example.rpc.transport;

import com.example.rpc.protocol.Peer;

import java.io.InputStream;

public interface TransportClient {

    void connect(Peer peer);

    InputStream write(InputStream data);

    void close();
}
