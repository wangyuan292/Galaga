package owen.galaga.rpc.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.reflect.FastClass;
import org.springframework.cglib.reflect.FastMethod;
import org.springframework.util.StringUtils;

import sun.rmi.runtime.Log;

import owen.galaga.rpc.common.base.RpcRequest;
import owen.galaga.rpc.common.base.RpcResponse;
import owen.galga.rpc.config.ProviderConfig;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

/**
 * @author Owen.Wang
 * @description: ServerHandler
 * @date 2020/5/30 12:05
 */
public class ServerHandler extends SimpleChannelInboundHandler<RpcRequest> {

    private final Map<String, Object> beanMap;

    private final Map<String, Object> handleMap;

    private static final Map<String, Channel> CHANNELS = new ConcurrentHashMap<>();

    private static final ExecutorService SHARED_EXECUTOR = Executors.newCachedThreadPool(new NamedThreadFactory("GalagaHandler", true));

    private static final Logger LOGGER = LoggerFactory.getLogger(ServerHandler.class);

    public ServerHandler(Map<String, Object> handleMapMap, Map<String, Object> beanMap){
        this.beanMap = beanMap;
        this.handleMap = handleMapMap;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, final RpcRequest request) {
        LOGGER.info("galaga server 请求 {} 服务[{}] -> 方法[{}]", request.getRequestId(), request.getClassName(), request.getMethodName());
        final String className = request.getClassName();

        ProviderConfig providerConfig = (ProviderConfig) handleMap.get(className);
        if (providerConfig == null){
            LOGGER.error("RuntimeException service bean not find");
            RpcResponse response = new RpcResponse();
            response.setResult(request.getRequestId());
            response.setError(new RuntimeException("service not found"));
            channelHandlerContext.writeAndFlush(response).addListeners(ChannelFutureListener.CLOSE);
        }

        final Channel channel = channelHandlerContext.channel();
        //CHANNELS.put(request.getRequestId(), channel);
        //LOGGER.info("hashCode {}", channel.hashCode());
        Class<?> serviceClass = beanMap.get(className).getClass();
        //Dispatcher
        GalagaHandler galagaHandler = new GalagaHandler(channel, request, serviceClass);
        SHARED_EXECUTOR.submit(galagaHandler);
    }

}
