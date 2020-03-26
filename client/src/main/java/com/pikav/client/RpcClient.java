package com.pikav.client;

import com.pikav.client.config.RpcClientConfig;
import com.pikav.codec.EnOrDecoder;
import com.pikav.utils.ReflectionUtils;

import java.lang.reflect.Proxy;

import static javafx.scene.input.KeyCode.T;

public class RpcClient {

    private RpcClientConfig clientConfig;

    private EnOrDecoder enOrDecoder;

    private TransportSelector selector;

    public RpcClient() {
        this(new RpcClientConfig());
    }

    public RpcClient(RpcClientConfig clientConfig) {
        this.clientConfig = clientConfig;
        this.enOrDecoder = ReflectionUtils.newInstance(this.clientConfig.getEnOrDecoderClass());
        this.selector = ReflectionUtils.newInstance(this.clientConfig.getSelectorClass());

        this.selector.init(
                this.clientConfig.getServers(),
                this.clientConfig.getConnectCount(),
                this.clientConfig.getTransportClass()
        );
    }

    public <T> T getProxy(Class<T> clazz) {
        return (T) Proxy.newProxyInstance(
                getClass().getClassLoader(),
                new Class[] {clazz},
                new RemoteInvoker(clazz, enOrDecoder, selector)
        );
    }
}
