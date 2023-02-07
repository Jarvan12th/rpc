package com.example.rpc.client;

import com.example.rpc.common.utils.ReflectionUtils;
import com.example.rpc.protocol.Peer;
import com.example.rpc.transport.TransportClient;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Slf4j
public class RandomTransportSelector implements TransportSelector {

    List<TransportClient> transportClients = new ArrayList<>();

    @Override
    public void init(List<Peer> peers, int count, Class<? extends TransportClient> clazz) throws Exception {
        count = Math.max(count, 1);

        for (Peer peer : peers) {
            initClient(peer, count, clazz);
        }
    }

    @Override
    public synchronized TransportClient select() {
        int index = new Random().nextInt(transportClients.size());
        return transportClients.remove(index);
    }

    @Override
    public void release(TransportClient client) {
        transportClients.add(client);
    }

    @Override
    public void close() {
        transportClients.forEach(TransportClient::close);
        transportClients.clear();
    }

    private void initClient(Peer peer, int count, Class<? extends TransportClient> clazz) throws Exception {
        for (int i = 0; i < count; i++) {
            TransportClient client = ReflectionUtils.newInstance(clazz);
            client.connect(peer);
            transportClients.add(client);
        }
        log.info("connect server: {}", peer);
    }
}
