package owen.galaga.rpc.filter.cache;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import owen.galaga.rpc.common.base.GSUrl;

/**
 * @Author :WangYuan
 * @Date :Created in 2020-09-08 20:42
 * @Description:
 * @Modified By:
 * @Version:1.0
 */
public abstract class AbstractCacheFactory implements CacheFactory {

    //缓存的缓存 :)
    private final ConcurrentMap<String, Cache> caches = new ConcurrentHashMap<String, Cache>();

    public Cache getCache(GSUrl url) {
        String key = url.cacheKey();
        Cache cache = caches.get(key);
        if (null == cache) {
            cache.put(key, createCache(url));
            cache = caches.get(key);
        }
        return cache;
    }

    protected abstract Cache createCache(GSUrl url);

}
