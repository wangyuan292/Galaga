package owen.galaga.rpc.filter.cache;

import owen.galaga.rpc.common.base.GSUrl;

public interface CacheFactory {

    Cache getCache(GSUrl url);

}
