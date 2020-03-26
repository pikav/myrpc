package com.pikav.server;

import com.pikav.proto.Request;
import com.pikav.proto.ServiceDescriptor;
import com.pikav.server.impl.TestClass;
import com.pikav.utils.ReflectionUtils;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;

import static org.junit.Assert.*;

public class ServiceManagerTest {

    ServiceManager sm = new ServiceManager();

    @Before
    public void init(){
        TestInterface bean = new TestClass();
        sm.register(TestInterface.class, bean);
    }

    @Test
    public void register() {
        TestInterface bean = new TestClass();
        sm.register(TestInterface.class, bean);
    }

    @Test
    public void lookup() {
        Method method = ReflectionUtils.getPublicMethods(TestInterface.class)[0];
        ServiceDescriptor serviceDescriptor = ServiceDescriptor.from(TestInterface.class, method);
        Request request = new Request();
        request.setServiceDescriptor(serviceDescriptor);
        ServiceInstance serviceInstance = sm.lookup(request);
        assertNotNull(serviceInstance);
    }

}