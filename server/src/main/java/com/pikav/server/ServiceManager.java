package com.pikav.server;

import com.pikav.proto.Request;
import com.pikav.proto.ServiceDescriptor;
import com.pikav.utils.ReflectionUtils;
import lombok.extern.slf4j.Slf4j;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 管理rpc暴露的服务， 注册服务， 查找服务
 *
 * @author pikav
 */
@Slf4j
public class ServiceManager {

    private Map<ServiceDescriptor, ServiceInstance> services;

    public ServiceManager() {
        this.services = new ConcurrentHashMap<>();
    }


    /**
     * 把接口方法和实现类绑定，形成ServiceInstance，装入services
     *
     * @param interfaceClass
     * @param bean
     * @param <T>
     */
    public <T> void register(Class<T> interfaceClass, T bean) {
        Method[] methods = ReflectionUtils.getPublicMethods(interfaceClass);
        for (Method method: methods) {
            ServiceInstance serviceInstance = new ServiceInstance(bean, method);
            ServiceDescriptor serviceDescriptor = ServiceDescriptor.from(interfaceClass,method);
            services.put(serviceDescriptor,serviceInstance);
            log.info("register service:{}{}" + serviceDescriptor.getClazz(), serviceDescriptor.getMethod());
        }
    }

    public ServiceInstance lookup(Request request) {
        ServiceDescriptor serviceDescriptor = request.getServiceDescriptor();
        return  services.get(serviceDescriptor);
    }

}
