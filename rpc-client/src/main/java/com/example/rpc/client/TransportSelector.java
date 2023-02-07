package com.example.rpc.client;

import com.example.rpc.protocol.Peer;
import com.example.rpc.transport.TransportClient;

import java.util.List;

public interface TransportSelector {

    void init(List<Peer> peers, int count, Class<? extends TransportClient> clazz) throws Exception;

    TransportClient select();

    void release(TransportClient client);

    void close();
}
