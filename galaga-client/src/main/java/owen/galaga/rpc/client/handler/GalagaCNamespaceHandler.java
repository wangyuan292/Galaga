package owen.galaga.rpc.client.handler;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * @author Owen.Wang
 * @description: TODO
 * @date 2020/5/31 16:14
 */
public class GalagaCNamespaceHandler extends NamespaceHandlerSupport {

    @Override
    public void init() {
        this.registerBeanDefinitionParser("registry", new GalagaClientBeanDefinitionParser());
        this.registerBeanDefinitionParser("consumer", new GalagaClientBeanDefinitionParser());
    }
}
