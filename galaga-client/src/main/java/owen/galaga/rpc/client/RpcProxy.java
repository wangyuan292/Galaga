package owen.galaga.rpc.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.util.CollectionUtils;

import java.util.List;

import owen.galaga.rpc.cluster.loadbalance.RandomLoadBalance;
import owen.galaga.rpc.common.base.GSUrl;
import owen.galaga.rpc.registry.ServerDiscovery;

import javax.annotation.Resource;

/**
 * @author Owen.Wang
 * @description: TODO
 * @date 2020/6/6 16:42
 */

public class RpcProxy<T> extends RandomLoadBalance implements FactoryBean<T> {

    @Resource
    private RegistryResource registryResource;

    @Resource
    private ServerDiscovery serverDiscovery;

    private String alias;
    private Integer timeout;
    private List<GSUrl> urls;
    private Class<T> interfaceClass;
    private static final Logger LOGGER = LoggerFactory.getLogger(RpcProxy.class);

    @Override
    public T getObject() throws Exception {
        loadProviders();
        //随机算法
        GSUrl gsUrl = doSelect(urls);
        Object object = new ConsumerProxy().bind(interfaceClass, gsUrl.getAddress());
        return (T) object;
    }

    private void loadProviders() {
        //从注册中心获取providers列表
        String serviceName = interfaceClass.getName();
        urls = serverDiscovery.findServerByName(serviceName, alias, registryResource.registryAddress());
        if (CollectionUtils.isEmpty(urls)) {
            throw new IllegalStateException("[Galaga-21001] no provider founded for service: " + serviceName);
        }
        //TODO telenet the sever before build proxy
    }

    @Override
    public Class<?> getObjectType() {
        return interfaceClass;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    public Class<T> getInterfaceClass() {
        return interfaceClass;
    }

    public void setInterfaceClass(Class<T> interfaceClass) {
        this.interfaceClass = interfaceClass;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }
}
