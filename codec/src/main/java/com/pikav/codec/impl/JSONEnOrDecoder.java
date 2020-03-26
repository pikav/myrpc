package com.pikav.codec.impl;

import com.alibaba.fastjson.JSON;
import com.pikav.codec.EnOrDecoder;

/**
 * 基于json序列化和反序列化实现
 *
 * @author pikav
 */
public class JSONEnOrDecoder implements EnOrDecoder {

    @Override
    public <T> T decode(byte[] bytes, Class<T> clazz) {
        return JSON.parseObject(bytes, clazz);
    }

    @Override
    public byte[] encode(Object obj) {
        return JSON.toJSONBytes(obj);
    }

}
