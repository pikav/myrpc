package com.pikav.utils;

import com.pikav.XmlNodes.RpcServiceXMLConfig;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import java.util.Iterator;

/**
 * 解析xml
 *
 * @author pikav
 */

@Slf4j
public class XmlUtil {

    public static RpcServiceXMLConfig rpcServiceConfig = new RpcServiceXMLConfig();

    public static void initRpcServiceConfig() {
        initRpcServiceConfig("common/src/main/resources/myrpc.xml");
    }

    public static void initRpcServiceConfig(String xmlPath) {
        SAXReader saxReader = new SAXReader();
        Document document = null;
        try {
            document = saxReader.read(xmlPath);
            Element elementRoot = document.getRootElement();
            for(Iterator elementIterator = elementRoot.elementIterator(); elementIterator.hasNext();){
                Element element = (Element)elementIterator.next();
                if("reference".equals(element.getName())) {
                    rpcServiceConfig.getReferenceMap().put(element.attributeValue("id"),element.attributeValue("interface"));
                } else if("bean".equals(element.getName())){
                    rpcServiceConfig.getBeanMap().put(element.attributeValue("id"),element.attributeValue("class"));
                } else {
                    rpcServiceConfig.getServiceMap().put(element.attributeValue("interface"),element.attributeValue("ref"));
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }
    }

    public static void main(String[] args) {
        initRpcServiceConfig();
    }

}
