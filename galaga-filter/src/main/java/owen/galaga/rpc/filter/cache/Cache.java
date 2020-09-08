package owen.galaga.rpc.filter.cache;

public interface Cache {

    void put(Object key, Object value);

    Object get(Object key);

}
