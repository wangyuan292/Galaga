package owen.galaga.rpc.cluster.loadbalance;

import java.util.List;

import owen.galaga.rpc.cluster.LoadBalance;
import owen.galaga.rpc.common.base.GSUrl;


public abstract class AbstractLoadBalance implements LoadBalance {

    public GSUrl select(List<GSUrl> urls) {
        if (null == urls || urls.size() == 0) {
            return null;
        }
        if (urls.size() == 1) {
            return urls.get(0);
        }
        return doSelect(urls);
    }

    protected abstract GSUrl doSelect(List<GSUrl> urls);

}
