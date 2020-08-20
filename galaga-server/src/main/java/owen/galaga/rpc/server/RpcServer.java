package owen.galaga.rpc.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import owen.galaga.rpc.common.base.GSUrl;
import owen.galaga.rpc.common.base.RpcRequest;
import owen.galaga.rpc.common.base.RpcResponse;
import owen.galaga.rpc.common.coder.RpcDecoder;
import owen.galaga.rpc.common.coder.RpcEncoder;
import owen.galaga.rpc.common.util.NetUtils;
import owen.galaga.rpc.registry.ServerRegistry;
import owen.galga.rpc.config.ProviderConfig;
import owen.galga.rpc.config.RegistryConfig;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

/**
 * @author Owen.Wang
 * @description: RPC Server
 * @date 2020/5/30 16:52
 */

@Component
public class RpcServer implements ApplicationContextAware, InitializingBean {

    @Resource
    private ServerRegistry serverRegistry;

    private RegistryConfig registryConfig;

    //Bean Map
    private Map<String, Object> beanMap = new HashMap<>();
    //Provider Map
    private Map<String, Object> handlerMap = new HashMap<>();

    private static final Logger LOGGER = LoggerFactory.getLogger(RpcServer.class);

    @Override
    public void afterPropertiesSet() throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new RpcDecoder(RpcRequest.class));
                            ch.pipeline().addLast(new RpcEncoder(RpcResponse.class));
                            ch.pipeline().addLast(new ServerHandler(handlerMap, beanMap));
                        }
                    }).option(ChannelOption.SO_BACKLOG, 1024)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            //本地端口不做特殊处理
            String host = NetUtils.getLocalHost();
            int port = NetUtils.getAvailablePort();
            ChannelFuture future = serverBootstrap.bind(host, port).sync();
            LOGGER.info("galaga server started on port {}", port);
            String serverAddress = host + ":" + port;

            //服务导出
            if (null != registryConfig) {
                for (String serviceName : handlerMap.keySet()) {
                    ProviderConfig config = (ProviderConfig) handlerMap.get(serviceName);
                    GSUrl gsUrl = buildGsurl(host, port, config, registryConfig.getRegistryAddress());
                    serverRegistry.registry(gsUrl);
                    LOGGER.info("注册服务 {} --> {}", serviceName, serverAddress);
                }
            }

            future.channel().closeFuture().sync();

        } catch (Exception e) {
            LOGGER.error(" ", e);
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    /**
     * ApplicationContextAware 容器初始化获取上下文中的所有bean
     *
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

        Map<String, ProviderConfig> providerConfigMap = applicationContext == null ? null : BeanFactoryUtils.beansOfTypeIncludingAncestors(applicationContext, ProviderConfig.class, false, false);
        //获取所有provider
        if (null != providerConfigMap && providerConfigMap.size() > 0) {
            for (String beanName : providerConfigMap.keySet()) {
                ProviderConfig providerConfig = providerConfigMap.get(beanName);
                String serviceName = providerConfig.getInterfaceI().getName();
                handlerMap.put(serviceName, providerConfig);
            }
        }

        //注册中心信息
        Map<String, RegistryConfig> registryConfigMap = applicationContext == null ? null : BeanFactoryUtils.beansOfTypeIncludingAncestors(applicationContext, RegistryConfig.class, false, false);
        if (null != registryConfigMap && registryConfigMap.size() > 0) {
            for (String beanName : registryConfigMap.keySet()) {
                registryConfig = registryConfigMap.get(beanName);
                break;
            }
        }

        //接口实现类
        Map<String, Object> implBeans = applicationContext.getBeansWithAnnotation(Galaga.class);
        if (!CollectionUtils.isEmpty(implBeans)){
            for (Object serviceBean: implBeans.values()) {
                Galaga galaga = serviceBean.getClass().getAnnotation(Galaga.class);
                String serviceName = galaga.value().getName();
                beanMap.put(serviceName, serviceBean);
            }
        }
    }

    private GSUrl buildGsurl(String host, int port, ProviderConfig providerConfig, String registryAddress) {
        GSUrl gsUrl = GSUrl.build(host, port, providerConfig.getInterfaceI().getName(), providerConfig.getAlias(), registryAddress);
        return gsUrl;
    }

}
