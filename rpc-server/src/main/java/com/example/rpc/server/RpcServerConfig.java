package com.example.rpc.server;

import com.example.rpc.serializer.Decoder;
import com.example.rpc.serializer.Encoder;
import com.example.rpc.transport.HTTPTransportServer;
import com.example.rpc.transport.TransportServer;
import lombok.Data;

@Data
public class RpcServerConfig {

    private Class<? extends TransportServer> transportClass = HTTPTransportServer.class;

    private Class<? extends Encoder> encoderClass = Encoder.class;

    private Class<? extends Decoder> decoderClass = Decoder.class;

    private int port = 3000;
}
