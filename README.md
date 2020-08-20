# Galaga

###### 实现功能
- 基于Spring的Schema扩展进行加载
- 基于netty实现底层传输调用,消息编组解组
- 基于Zookeeper实现了服务的自动注册与发现
- 实现了服务的多版本支持与负载均衡(随机算法)

###### 未实现功能
- invoker列表刷新
- 快速失败,重试机制(容灾)
- 框架自适应扩展机制
- 领域模型 invoker protocol
- 多注册中心实现
- ...

#### 快速启动
1. provider 配置
```xml
<galaga:registry id="testRegistry" protocol="zookeeper" address="192.168.31.130:2181"/>
<galaga:provider id="id" interface="owen.galaga.rpc.demo.api.HelloService" alias="dev" ref="helloServiceImpl" timeout="1000" />
```
2. consumer 配置
```xml
<galaga:registry id="testRegistry" protocol="zookeeper" address="192.168.31.130:2181"/>
<galaga:consumer id="helloService" interface="owen.galaga.rpc.demo.api.HelloService" alias="dev" timeout="1000"/>
```

3. demo
```java
public static void main(String[] args) throws InterruptedException {
      ApplicationContext applicationContext = new ClassPathXmlApplicationContext("Spring-Galaga-config.xml");
      HelloService helloService = applicationContext.getBean(HelloService.class);
      String str = helloService.hello("galaga " + System.currentTimeMillis());
      System.out.println(str);
  }
```

##### 源码分析

1. ###### RPC调用过程
![调用过程](http://dubbo.apache.org/docs/zh-cn/source_code_guide/sources/images/send-request-process.jpg)
  `首先服务消费者通过代理对象Proxy发起远程调用,接着通过网络客户端Client将编码后的请求发送给服务提供方的网络层上,也就是Server.Server在收到请求后,首先要做的事情是对数据包进行解码.然后将解码后的请求发送至分发器Dispatcher,再由分发器将请求派发到指定的线程池上,最后由线程池调用具体的服务`

 ![](https://ftp.bmp.ovh/imgs/2020/08/9b507dff7dd1b1b8.jpg)
