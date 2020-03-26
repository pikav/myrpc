package com.pikav.example;

import com.pikav.server.RpcServer;
import com.pikav.utils.ReflectionUtils;
import com.pikav.utils.XmlUtil;
import java.util.Iterator;
import java.util.Map;
public class Server {

    public static void main(String[] args) {

        XmlUtil.initRpcServiceConfig();
        RpcServer server = new RpcServer();
        Iterator<Map.Entry<String,String>> iterator = XmlUtil.rpcServiceConfig.getServiceMap().entrySet().iterator();
            while (iterator.hasNext()) {
            Map.Entry<String,String> entry = iterator.next();
            server.register(ReflectionUtils.getClassByName(entry.getKey()),
                    ReflectionUtils.newInstance(ReflectionUtils.getClassByName(XmlUtil.rpcServiceConfig.getBeanMap().get(entry.getValue()))));
        }
        server.start();
    }

}
