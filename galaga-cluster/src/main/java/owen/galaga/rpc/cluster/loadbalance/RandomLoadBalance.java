package owen.galaga.rpc.cluster.loadbalance;


import java.util.List;
import java.util.Random;

import owen.galaga.rpc.common.base.GSUrl;

public class RandomLoadBalance extends AbstractLoadBalance {

    private final Random random = new Random();

    protected GSUrl doSelect(List<GSUrl> urls) {
        int length = urls.size();
        return urls.get(random.nextInt(length));
    }
}
