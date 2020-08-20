package owen.galaga.rpc.server.demo;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.UUID;

/**
 * @Author :WangYuan
 * @Date :Created in 2020-06-10 18:35
 * @Description:
 * @Modified By:
 * @Version:1.0
 */
public class ServerDemoTest {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("Spring-Galaga-config.xml");
    }


}
