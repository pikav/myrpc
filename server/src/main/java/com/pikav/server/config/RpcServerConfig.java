package com.pikav.server.config;

import com.pikav.codec.EnOrDecoder;
import com.pikav.codec.impl.JSONEnOrDecoder;
import com.pikav.transport.TransportServer;
import com.pikav.transport.impl.HttpTransportServer;
import lombok.Data;

/**
 * server配置
 *
 * @author pikav
 */

@Data
public class RpcServerConfig {

    private Class<? extends TransportServer> transportClass = HttpTransportServer.class;

    private Class<? extends EnOrDecoder> enOrDecoderClass = JSONEnOrDecoder.class;

    private int port = 2000;

}
