package com.pikav.codec;


/**
 * 序列化，反序列化：把对象转换成二进制(byte)数组，反之
 *
 * @author pikav
 */
public interface EnOrDecoder {

    <T> T decode(byte[] bytes, Class<T> Clazz);

    byte[] encode(Object obj);
}
