package owen.galaga.rpc.server.handler;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * @author Owen.Wang
 * @description: TODO
 * @date 2020/5/31 16:14
 */
public class GalagaSNamespaceHandler extends NamespaceHandlerSupport {

    @Override
    public void init() {
        this.registerBeanDefinitionParser("registry", new GalagaServerDefinitionParser());
        this.registerBeanDefinitionParser("provider", new GalagaServerDefinitionParser());
    }
}
