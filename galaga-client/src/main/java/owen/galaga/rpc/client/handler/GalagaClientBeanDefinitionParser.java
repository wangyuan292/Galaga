package owen.galaga.rpc.client.handler;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

import owen.galaga.rpc.client.RpcProxy;
import owen.galga.rpc.config.ConsumerConfig;
import owen.galga.rpc.config.RegistryConfig;

/**
 * @author Owen.Wang
 * @description: TODO
 * @date 2020/5/31 16:26
 */
public class GalagaClientBeanDefinitionParser implements BeanDefinitionParser {

    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext) {
        String id = element.getAttribute("id");
        if (StringUtils.isEmpty(id)) {
            throw new IllegalStateException("[Galaga-21000]This bean do not set spring bean id " + id);
        } else {
            switch (element.getLocalName()){
                case "registry" :
                    return parseRegistryConfig(element, parserContext);
                case "consumer" :
                    return parseConsumerConfig(element, parserContext);
                default:
                    break;
            }
        }
        return null;
    }


    private BeanDefinition parseRegistryConfig(Element element, ParserContext parserContext) {
        String beanId = element.getAttribute("id");
        String addresss = element.getAttribute("address");
        String protocol = element.getAttribute("protocol");
        String connectTimeout = element.getAttribute("connectTimeout");

        RootBeanDefinition definition = new RootBeanDefinition();
        definition.getPropertyValues().add("protocol", protocol);
        definition.getPropertyValues().add("registryAddress", addresss);
        definition.getPropertyValues().add("connectTimeout", connectTimeout);
        definition.setBeanClass(RegistryConfig.class);
        definition.setAutowireMode(RootBeanDefinition.AUTOWIRE_BY_TYPE);
        parserContext.getRegistry().registerBeanDefinition(beanId, definition);
        return definition;
    }

    private BeanDefinition parseConsumerConfig(Element element, ParserContext parserContext) {
        //<galaga:consumer id="testConsum" interface="com.test." alias="dev" timeout="1000"/>
        String beanId = element.getAttribute("id");
        String alias = element.getAttribute("alias");
        String timeOut = element.getAttribute("timeOut");
        String interfaceI = element.getAttribute("interface");

        RootBeanDefinition definition = new RootBeanDefinition();
        Class<?> cls = null;
        try {
            cls = Class.forName(interfaceI);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        definition.getPropertyValues().add("alias", alias);
        definition.getPropertyValues().add("interfaceClass", cls);
        definition.setBeanClass(RpcProxy.class);
        definition.setAutowireMode(RootBeanDefinition.AUTOWIRE_BY_TYPE);
        parserContext.getRegistry().registerBeanDefinition(beanId, definition);
        return definition;
    }


    /*//backUp
    private BeanDefinition parseConsumerConfig(Element element, ParserContext parserContext) {
        //<galaga:consumer id="testConsum" interface="com.test." alias="dev" timeout="1000"/>
        String beanId = element.getAttribute("id");
        String alias = element.getAttribute("alias");
        String timeOut = element.getAttribute("timeOut");
        String interfaceI = element.getAttribute("interface");

        RootBeanDefinition definition = new RootBeanDefinition();
        definition.getPropertyValues().add("alias", alias);
        definition.getPropertyValues().add("interfaceI", interfaceI);
        definition.setBeanClass(ConsumerConfig.class);
        definition.setAutowireMode(RootBeanDefinition.AUTOWIRE_BY_TYPE);
        parserContext.getRegistry().registerBeanDefinition(beanId, definition);
        return definition;
    }*/
}


