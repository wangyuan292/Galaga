package owen.galaga.rpc.server.handler;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

import owen.galga.rpc.config.ProviderConfig;
import owen.galga.rpc.config.RegistryConfig;

/**
 * @author Owen.Wang
 * @description: TODO
 * @date 2020/5/31 16:26
 */
public class GalagaServerDefinitionParser implements BeanDefinitionParser {

    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext) {
        String id = element.getAttribute("id");
        if (StringUtils.isEmpty(id)) {
            throw new IllegalStateException("[Galaga-21000]This bean do not set spring bean id " + id);
        } else {
            switch (element.getLocalName()){
                case "registry" :
                    return parseRegistryConfig(element, parserContext);
                case "provider" :
                    return parseProviderConfig(element, parserContext);
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

    private BeanDefinition parseProviderConfig(Element element, ParserContext parserContext) {
        //<galaga:consumer id="testConsum" interface="com.test." alias="dev" timeout="1000"/>
        String beanId = element.getAttribute("id");
        String alias = element.getAttribute("alias");
        String timeOut = element.getAttribute("timeOut");
        String interfaceName = element.getAttribute("interface");

        RootBeanDefinition definition = new RootBeanDefinition();
        definition.getPropertyValues().add("alias", alias);
        definition.getPropertyValues().add("interfaceI", interfaceName);
        definition.setBeanClass(ProviderConfig.class);
        definition.setAutowireMode(RootBeanDefinition.AUTOWIRE_BY_TYPE);
        parserContext.getRegistry().registerBeanDefinition(beanId, definition);
        return definition;
    }


}


