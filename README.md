工作时经常使用webservice, 前段时间又学习实践了一下dubbo, 所以今天又学习手写了一个rpc框架

总结：
  一、 rpc架构需要四个核心组件：
    1. 客户端：服务的调用方
    2. 客户端存根：存放服务端地址信息，将客户端的请求参数打包成网络消息，然后远程发送给服务端
    3. 服务端：服务提供者
    4. 服务端存根：接收客户端发送过来的消息，解析消息，并且调用本地方法
    
  二、rpc的调用过程：
    1. 客户端以接口的形式调用服务
    2. 客户端存根接收到调用后，将方法、参数组装成消息体并且序列化成二进制
    3. 然后通过sockets将消息发送到服务端
    4. 服务的存根收到消息，解析，反序列化，根据解析结果调用本地服务获得返回结果
    5. 然后将结果打包成消息，序列化
    6. 服务端同样通过sockets将消息发送到客户端
    7. 客户端存根接收到消息、解析，返回最终结果给客户端
    (而我写的RPC框架其实就是把消息序列化、反序列化、消息通过网络传输这些步骤进行了封装)
    
  项目介绍：
  
    1. 在xml中配置要发布的服务(跟dubbo一样)
      <?xml version="1.0" encoding="UTF-8"?>
      <beans>
          <bean id="testService" class="com.pikav.example.impl.CalcServiceImpl"/>
          <service interface="com.pikav.example.CalcService" ref="testService"/>
          
          <reference id="testService" interface="com.pikav.example.CalcService"/>
      </beans>
      
    2. 启动服务代码
      XmlUtil.initRpcServiceConfig();
      RpcServer server = new RpcServer();
      Iterator<Map.Entry<String,String>> iterator = XmlUtil.rpcServiceConfig.getServiceMap().entrySet().iterator();
          while (iterator.hasNext()) {
          Map.Entry<String,String> entry = iterator.next();
          server.register(ReflectionUtils.getClassByName(entry.getKey()),
                  ReflectionUtils.newInstance(ReflectionUtils.getClassByName(XmlUtil.rpcServiceConfig.getBeanMap().get(entry.getValue()))));
      }
      server.start();
      
    3. 调用服务
      RpcClient client = new RpcClient();
      CalcService service = client.getProxy(CalcService.class);
      int r1 = service.add(1, 2);
      
   执行原理：
    1. 服务端：读取xml的服务配置, 将信息保存在一个map中, 然后用jetty动态的生成一个servlet用于接收客户端的调用信息, 
       重写servlet的doPost方法, 方法里面解析客户端发送过来的数据, 并且根据数据找到对应的本地方法执行,并且将结果写入response中返回。
    2. 客户端：通过接口生成一个代理对象, 代理对像调用目标方法时执行invoke方法, invoke方法中携带接口信息发送一个请求至服务端,
       调用其doPost方法, 并且接收响应数据。
      
      
      

