package com.pikav.client;

import com.pikav.codec.EnOrDecoder;
import com.pikav.proto.Request;
import com.pikav.proto.Response;
import com.pikav.proto.ServiceDescriptor;
import com.pikav.transport.TransportClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 调用远程服务的代理类
 *
 * @author pikav
 */

@Slf4j
public class RemoteInvoker implements InvocationHandler {

    private Class clazz;

    private EnOrDecoder enOrDecoder;

    private TransportSelector selector;

    public RemoteInvoker(Class clazz, EnOrDecoder enOrDecoder, TransportSelector selector) {
        this.clazz = clazz;
        this.enOrDecoder = enOrDecoder;
        this.selector = selector;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Request request = new Request();
        request.setServiceDescriptor(ServiceDescriptor.from(clazz, method));
        request.setParameters(args);

        Response resp = invokeRemote(request);
        if (resp == null || resp.getCode() != 0) {
            throw new IllegalStateException("fail to invoke remote:" + resp);
        }

        return resp.getData();
    }

    private Response invokeRemote(Request request) {
        Response response = null;
        TransportClient client = null;
        try {
            client = selector.select();
            byte[] outBytes = enOrDecoder.encode(request);
            InputStream revice = client.write(new ByteArrayInputStream(outBytes));
            byte[] inBytes = IOUtils.readFully(revice, revice.available());
            response = enOrDecoder.decode(inBytes, Response.class);
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
            response = new Response();
            response.setCode(1);
            response.setMsg("RpcClient got error:" + e.getCause() + " : " +e.getMessage());
        } finally {
            if (selector != null) selector.release(client);
        }
        return response;
    }

}
