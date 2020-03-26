package com.pikav.proto;

import lombok.Data;

/***
 * 表示RPC返回
 *
 * @author pikav
 */

@Data
public class Response {

    // 服务返回编码, 0成功，否在失败
    private int code = 0;

    private String msg = "ok";

    private Object data;

}
