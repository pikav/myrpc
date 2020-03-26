package com.pikav.transport.impl;

import com.pikav.proto.Peer;
import com.pikav.transport.TransportClient;
import org.apache.commons.io.IOUtils;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HTTPTransportClient implements TransportClient {

    private String url;

    /**
     * http短连接(dubbo是单一长连接,适用于高并发小数据)
     *
     * @param peer
     */
    @Override
    public void connect(Peer peer) {
        this.url = "http://" + peer.getHost() + ":" + peer.getPort();
    }

    @Override
    public InputStream write(InputStream data) {
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(url).openConnection();
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setRequestMethod("POST");
            IOUtils.copy(data,httpURLConnection.getOutputStream());
            int resultCode = httpURLConnection.getResponseCode();
            if(HttpURLConnection.HTTP_OK == resultCode) {
                return httpURLConnection.getInputStream();
            } else {
                return httpURLConnection.getErrorStream();
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void close() {

    }
}
