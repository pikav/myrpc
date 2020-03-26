package com.pikav.server;

import com.pikav.codec.EnOrDecoder;
import com.pikav.proto.Request;
import com.pikav.proto.Response;
import com.pikav.server.config.RpcServerConfig;
import com.pikav.transport.RequestHandler;
import com.pikav.transport.TransportServer;
import com.pikav.utils.ReflectionUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 *
 *
 * @author pikav
 */

@Slf4j
public class RpcServer {

    private RpcServerConfig rpcServerConfig;
    private TransportServer transportServer;
    private EnOrDecoder enOrDecoder;
    private ServiceManager serviceManager;
    private ServiceInvoker serviceInvoker;

    private RequestHandler hanlder = new RequestHandler() {
        @Override
        public void onRequest(InputStream recive, OutputStream toResp) {
            Response response = new Response();
            try {
                byte[] inBytes = IOUtils.readFully(recive, recive.available());
                Request request = enOrDecoder.decode(inBytes,Request.class);
                log.info("get request: {}", request);
                ServiceInstance serviceInstance = serviceManager.lookup(request);
                Object ret = serviceInvoker.invoke(serviceInstance, request);
                response.setData(ret);
            } catch (IOException e) {
                log.warn(e.getMessage(),e);
                response.setCode(1);
                response.setMsg("RPC server get error:" + e.getClass().getName() + " : " + e.getMessage());
            } finally {
                byte[] outBytes = enOrDecoder.encode(response);
                try {
                    toResp.write(outBytes);
                    log.info("response client");
                } catch (IOException e) {
                    log.warn(e.getMessage(), e);
                }
            }
        }
    };

    public  RpcServer(RpcServerConfig config) {
        this.rpcServerConfig = config;

        this.transportServer = ReflectionUtils.newInstance(config.getTransportClass());
        this.transportServer.init(config.getPort(), this.hanlder);

        this.enOrDecoder = ReflectionUtils.newInstance(config.getEnOrDecoderClass());

        this.serviceManager = new ServiceManager();

        this.serviceInvoker = new ServiceInvoker();
    }

    public RpcServer() {
        this(new RpcServerConfig());
    }

    public void start() {
        this.transportServer.start();
    }

    public void stop() {
        this.transportServer.stop();
    }

    public <T> void register(Class<T> interfaceClass, T bean){
        serviceManager.register(interfaceClass,bean);
    }

}
