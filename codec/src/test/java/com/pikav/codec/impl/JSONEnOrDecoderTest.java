package com.pikav.codec.impl;

import com.pikav.codec.EnOrDecoder;
import org.junit.Test;

import static org.junit.Assert.*;

public class JSONEnOrDecoderTest {

    @Test
    public void decode() {
        EnOrDecoder enOrDecoder = new JSONEnOrDecoder();
        TestBean bean = new TestBean();
        bean.setName("pikav");
        bean.setAge(24);
        byte[] bytes = enOrDecoder.encode(bean);

        TestBean bean1 = enOrDecoder.decode(bytes, TestBean.class);

    }

    @Test
    public void encode() {
        EnOrDecoder enOrDecoder = new JSONEnOrDecoder();
        TestBean bean = new TestBean();
        bean.setName("pikav");
        bean.setAge(24);
        byte[] bytes = enOrDecoder.encode(bean);
        assertNotNull(bytes);
    }
}