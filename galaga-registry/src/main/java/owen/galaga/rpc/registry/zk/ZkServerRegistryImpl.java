package owen.galaga.rpc.registry.zk;

import org.I0Itec.zkclient.ZkClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;

import owen.galaga.rpc.common.Constants;
import owen.galaga.rpc.common.base.GSUrl;
import owen.galaga.rpc.registry.ServerRegistry;

/**
 * @author Owen.Wang
 * @description:
 * @date 2020/5/30 10:58
 */
@Service
public class ZkServerRegistryImpl implements ServerRegistry {

    private ZkClient zkClient;

    private static final Logger LOGGER = LoggerFactory.getLogger(ZkServerRegistryImpl.class);

    public void registry(GSUrl data) {

        if (null == zkClient) {
            zkClient = new ZkClient(data.getRegistryAddress(), 5000, 1000);
            LOGGER.info("初始化注册中心...");
        }

        String registryPath = Constants.ZK_REGISTRY_PATH;
        if (!zkClient.exists(registryPath)){
            zkClient.createPersistent(registryPath);
            LOGGER.debug("注册 {}", Constants.ZK_REGISTRY_PATH);
        }
        String iFacePath = registryPath + "/" + data.getIface();
        if (!zkClient.exists(iFacePath)){
            zkClient.createPersistent(iFacePath);
            LOGGER.debug("注册 {}", iFacePath);
        }

        String serverPath = registryPath + "/" + data.getIface() + "/" + data.getAlias();
        if (!zkClient.exists(serverPath)){
            zkClient.createPersistent(serverPath);
            LOGGER.debug("注册 {}", serverPath);
        }

        String addressNode = zkClient.createEphemeralSequential(serverPath + "/" + "node", data.toString());
        LOGGER.debug("注册临时节点 "+ serverPath);
    }
}
