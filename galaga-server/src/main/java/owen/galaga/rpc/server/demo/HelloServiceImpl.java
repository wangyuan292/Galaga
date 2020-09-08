package owen.galaga.rpc.server.demo;

import org.springframework.stereotype.Service;

import owen.galaga.rpc.demo.api.HelloService;

/**
 * @author Owen.Wang
 * @description: Galaga
 * @date 2020/6/10 18:40
 */

@Service
public class HelloServiceImpl implements HelloService {

    @Override
    public String hello(String name) {
        return "hello, " + name;
    }

}
