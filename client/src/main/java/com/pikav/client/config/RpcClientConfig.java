package com.pikav.client.config;

import com.pikav.client.RandomTransportSelector;
import com.pikav.client.TransportSelector;
import com.pikav.codec.EnOrDecoder;
import com.pikav.codec.impl.JSONEnOrDecoder;
import com.pikav.proto.Peer;
import com.pikav.transport.TransportClient;
import com.pikav.transport.impl.HTTPTransportClient;
import lombok.Data;

import java.util.Arrays;
import java.util.List;

/**
 *  客户端配置
 *
 * @author pikav
 * */
@Data
public class RpcClientConfig {

    private Class<? extends TransportClient> transportClass = HTTPTransportClient.class;

    private Class<? extends EnOrDecoder> enOrDecoderClass = JSONEnOrDecoder.class;

    private Class<? extends TransportSelector> selectorClass = RandomTransportSelector.class;

    private int connectCount = 1;

    private List<Peer> servers = Arrays.asList(new Peer("127.0.0.1", 2000));



}
