package owen.galaga.rpc.filter.cache.support.threadlocal;

import java.util.HashMap;
import java.util.Map;

import owen.galaga.rpc.common.base.GSUrl;
import owen.galaga.rpc.filter.cache.Cache;

/**
 * @Author :WangYuan
 * @Date :Created in 2020-09-08 21:04
 * @Description: ThreadLocalCache
 * @Modified By:
 * @Version:1.0
 */
public class ThreadLocalCache implements Cache {

    private final ThreadLocal<Map<Object, Object>> store;

    public ThreadLocalCache (GSUrl url) {
        this.store = new ThreadLocal<Map<Object, Object>>() {
            @Override
            protected Map<Object, Object> initialValue() {
                return new HashMap<Object, Object>();
            }
        };
    }

    public void put(Object key, Object value) {
        store.get().put(key, value);
    }

    public Object get(Object key) {
        return store.get().get(key);
    }
}
