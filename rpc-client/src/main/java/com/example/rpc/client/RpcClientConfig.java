package com.example.rpc.client;

import com.example.rpc.protocol.Peer;
import com.example.rpc.serializer.Decoder;
import com.example.rpc.serializer.Encoder;
import com.example.rpc.serializer.JSONDecoder;
import com.example.rpc.serializer.JSONEncoder;
import com.example.rpc.transport.HTTPTransportClient;
import com.example.rpc.transport.TransportClient;
import lombok.Data;

import java.util.Arrays;
import java.util.List;

@Data
public class RpcClientConfig {

    private Class<? extends TransportClient> transportClientClass = HTTPTransportClient.class;

    private Class<? extends Encoder> encoderClass = JSONEncoder.class;

    private Class<? extends Decoder> decoderClass = JSONDecoder.class;

    private Class<? extends TransportSelector> selectorClass = RandomTransportSelector.class;

    private int connectCount = 1;

    private List<Peer> peers = Arrays.asList(new Peer("127.0.0.1", 3000));
}
