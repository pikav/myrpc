package com.pikav.proto;

import lombok.Data;

/***
 * 表示请求
 *
 * @author pikav
 */

@Data
public class Request {

    private ServiceDescriptor serviceDescriptor;

    private Object[] parameters;
}
