package owen.galaga.rpc.client.demo;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import owen.galaga.rpc.demo.api.HelloService;

/**
 * @Author :WangYuan
 * @Date :Created in 2020-08-12 16:31
 * @Description:
 * @Modified By:
 * @Version:1.0
 */
public class ClientDemoTest {

    public static void main(String[] args) throws InterruptedException {

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("Spring-Galaga-config.xml");

        HelloService helloService = applicationContext.getBean(HelloService.class);

        //String str = helloService.hello("galaga " + System.currentTimeMillis());
        //System.out.println(str);

        //multiThread
        for (int i = 3; i > 0; i--) {
            simulateMT(helloService);
        }

    }

    private static void simulateMT(HelloService helloService) {
        Thread thread = new Thread(() -> {
            String str = helloService.hello("galaga " + System.currentTimeMillis());
            System.out.println(str);
        });
        thread.start();
    }

}
