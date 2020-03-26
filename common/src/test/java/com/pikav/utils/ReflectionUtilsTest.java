package com.pikav.utils;

import org.junit.Test;

import java.lang.reflect.Method;

import static org.junit.Assert.*;

public class ReflectionUtilsTest {

    @Test
    public void newInstance() {
        TestClass t = ReflectionUtils.newInstance(TestClass.class);
        assertNotNull(t);
    }

    @Test
    public void getPublicMethods() {
        Method[] methods = ReflectionUtils.getPublicMethods(TestClass.class);
        assertEquals(1,methods.length);
        String mName = methods[0].getName();
        assertEquals("pi",mName);
    }

    @Test
    public void invoke() {
        TestClass t = ReflectionUtils.newInstance(TestClass.class);
        Method[] methods = ReflectionUtils.getPublicMethods(TestClass.class);
        Method pi = methods[0];
        Object r = ReflectionUtils.invoke(t,pi);
        assertEquals("pi",r);
    }
}