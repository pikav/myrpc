package com.pikav.server;

import com.pikav.proto.Request;
import com.pikav.utils.ReflectionUtils;

/**
 * 服务调用
 *
 * @author pikav
 */

public class ServiceInvoker {

    public Object invoke(ServiceInstance serviceInstance, Request request) {
        return ReflectionUtils.invoke(serviceInstance.getTarget(),serviceInstance.getMethod(),request.getParameters());
    }

}
