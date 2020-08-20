package owen.galaga.rpc.registry;

import java.util.List;

import owen.galaga.rpc.common.base.GSUrl;

/**
 * @author Owen.Wang
 * @description:
 * @date 2020/5/30 10:57
 */
public interface ServerDiscovery {

    List<GSUrl> findServerByName(String serverName, String alias, String address);
}
