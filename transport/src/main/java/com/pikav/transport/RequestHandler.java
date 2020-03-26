package com.pikav.transport;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * 处理网络请求的handler
 *
 * @author pikav
 */
public interface RequestHandler {

    void onRequest(InputStream recive, OutputStream toResp);

}
