package owen.galaga.rpc.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import owen.galaga.rpc.common.base.RpcRequest;
import owen.galaga.rpc.common.base.RpcResponse;


public class GalagaHandler implements Callable<RpcResponse> {
    /**
     * Computes a result, or throws an exception if unable to do so.
     *
     * @return computed result
     * @throws Exception if unable to compute a result
     */

    private Channel channel;
    private Class<?> serviceClass;
    private RpcRequest rpcRequest;
    private RpcResponse rpcResponse;

    private static final Logger LOGGER = LoggerFactory.getLogger(GalagaHandler.class);

    public GalagaHandler() {}

    public GalagaHandler(Channel channel, RpcRequest rpcRequest, Class<?> serviceClass) {
        this.channel = channel;
        this.rpcRequest = rpcRequest;
        this.serviceClass = serviceClass;
    }

    @Override
    public RpcResponse call() throws Exception {

        channel.newSucceededFuture().addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {

                //TODO 模拟业务处理
                Random random = new Random();
                TimeUnit.SECONDS.sleep(random.nextInt(10));

                // 通过反射获取对象
                String methodName = rpcRequest.getMethodName();
                Class<?>[] parameterTypes = rpcRequest.getParameterTypes();
                Object[] parameters = rpcRequest.getParameters();

                Object object = null;
                try {
                    Method method = serviceClass.getMethod(methodName, parameterTypes);
                    object = method.invoke(serviceClass.newInstance(), parameters);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // 构造repsonse
                rpcResponse = new RpcResponse();
                rpcResponse.setRequestId(rpcRequest.getRequestId());
                rpcResponse.setResult(object);
                LOGGER.info(Thread.currentThread().getName());
                LOGGER.info("galaga server 响应 {} 结果 {}", rpcRequest.getRequestId(), rpcResponse.toString());
                channel.writeAndFlush(rpcResponse).addListener(ChannelFutureListener.CLOSE);
            }
        });
        return rpcResponse;
    }
}
