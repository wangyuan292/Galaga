package owen.galaga.rpc.filter.cache.support.threadlocal;

import owen.galaga.rpc.common.base.GSUrl;
import owen.galaga.rpc.filter.cache.AbstractCacheFactory;
import owen.galaga.rpc.filter.cache.Cache;

/**
 * @Author :WangYuan
 * @Date :Created in 2020-09-08 21:02
 * @Description:
 * @Modified By:
 * @Version:1.0
 */
public class ThreadLocalCacheFactory extends AbstractCacheFactory {

    protected Cache createCache(GSUrl url) {
        return new ThreadLocalCache(url);
    }
}
