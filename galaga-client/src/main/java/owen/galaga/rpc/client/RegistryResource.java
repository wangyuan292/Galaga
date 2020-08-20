package owen.galaga.rpc.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;
import javax.annotation.Resource;

import owen.galga.rpc.config.RegistryConfig;

/**
 * @author Owen.Wang
 * @description: TODO
 * @date 2020/6/6 14:39
 */

@Component
public class RegistryResource implements ApplicationContextAware {

    private RegistryConfig registryConfig;

    private static final Logger LOGGER = LoggerFactory.getLogger(RegistryResource.class);

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

        //注册中心信息
        Map<String, RegistryConfig> registryConfigMap = applicationContext == null ? null : BeanFactoryUtils.beansOfTypeIncludingAncestors(applicationContext, RegistryConfig.class, false, false);
        if (null != registryConfigMap && registryConfigMap.size() > 0) {
            for (String beanName : registryConfigMap.keySet()) {
                registryConfig = registryConfigMap.get(beanName);
                break;
            }
            LOGGER.info("加载注册中心配置 {}", registryConfig.getRegistryAddress());
        } else {
            throw new IllegalStateException("[Galaga-21002] no registry config founded");
        }
    }

    public String registryAddress() {
        return registryConfig.getRegistryAddress();
    }
}
