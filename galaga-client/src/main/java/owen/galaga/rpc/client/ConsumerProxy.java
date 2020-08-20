package owen.galaga.rpc.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import owen.galaga.rpc.common.base.RpcRequest;
import owen.galaga.rpc.common.base.RpcResponse;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.UUID;

/**
 * @author Owen.Wang
 * @description: TODO
 * @date 2020/5/31 13:52
 */
public class ConsumerProxy implements InvocationHandler {

    private String serverAddress;

    private Class<?> interfaceClass;

    private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerProxy.class);

    public Object bind(Class<?> cls, String serverAddress) {
        this.interfaceClass = cls;
        this.serverAddress = serverAddress;
        Object proxyO = Proxy.newProxyInstance(cls.getClassLoader(), new Class[] {interfaceClass}, this);
        return proxyO;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        RpcRequest request = new RpcRequest();
        request.setRequestId(UUID.randomUUID().toString().replace("-",""));
        request.setClassName(method.getDeclaringClass().getName());
        request.setVersion(null);
        request.setMethodName(method.getName());
        request.setParameters(args);
        request.setParameterTypes(method.getParameterTypes());
        String[] ipAndPort = StringUtils.split(serverAddress, ":");
        String ip = ipAndPort[0];
        Integer port = Integer.valueOf(ipAndPort[1]);

        RpcClient rpcClient = new RpcClient(ip, port);
        long time = System.currentTimeMillis();
        LOGGER.info("Request {}", request.toString());
        RpcResponse response = rpcClient.send(request);
        LOGGER.debug("调用耗时: {}",System.currentTimeMillis()-time);
        if (response == null){
            LOGGER.error("返回为空");
        } else {
            if (null != response.getError()){
                LOGGER.error("返回中带有异常");
                throw new RuntimeException("返回有异常");
            }
            return response.getResult();
        }
        return null;
    }

}
