package owen.galaga.rpc.cluster;

import java.util.List;

import owen.galaga.rpc.common.base.GSUrl;

public interface LoadBalance {

    /**
     * select one GSUrl in list.
     *
     * @param urls  urls
     * @return selected GSUrl
     */
    GSUrl select(List<GSUrl> urls);

}
