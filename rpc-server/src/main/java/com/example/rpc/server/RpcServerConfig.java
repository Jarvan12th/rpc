package com.example.rpc.server;

import com.example.rpc.serializer.Decoder;
import com.example.rpc.serializer.Encoder;
import com.example.rpc.serializer.JSONDecoder;
import com.example.rpc.serializer.JSONEncoder;
import com.example.rpc.transport.HTTPTransportServer;
import com.example.rpc.transport.TransportServer;
import lombok.Data;

@Data
public class RpcServerConfig {

    private Class<? extends TransportServer> transportClass = HTTPTransportServer.class;

    private Class<? extends Encoder> encoderClass = JSONEncoder.class;

    private Class<? extends Decoder> decoderClass = JSONDecoder.class;

    private int port = 3000;
}
