package com.pikav.XmlNodes;

import lombok.Data;
import java.util.HashMap;
import java.util.Map;

@Data
public class RpcServiceXMLConfig {

    private Map<String, String> beanMap;

    private Map<String, String> serviceMap;

    private Map<String, String> referenceMap;

    public RpcServiceXMLConfig() {
        this.beanMap = new HashMap<>();
        this.serviceMap = new HashMap<>();
        this.referenceMap = new HashMap<>();
    }

}
