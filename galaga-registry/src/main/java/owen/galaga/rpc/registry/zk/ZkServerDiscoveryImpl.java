package owen.galaga.rpc.registry.zk;


import org.I0Itec.zkclient.ZkClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import owen.galaga.rpc.common.Constants;
import owen.galaga.rpc.common.base.GSUrl;
import owen.galaga.rpc.registry.ServerDiscovery;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Owen.Wang
 * @description:
 * @date 2020/5/30 10:57
 */
@Service
public class ZkServerDiscoveryImpl implements ServerDiscovery {

    private static final Logger LOGGER = LoggerFactory.getLogger(ZkServerDiscoveryImpl.class);

    public List<GSUrl> findServerByName(String serverName, String alias, String address) {

        ZkClient zkClient = new ZkClient(address,5000, 1000);
        try {
            String serverPath = Constants.ZK_REGISTRY_PATH + "/" + serverName + "/" + alias;
            if (!zkClient.exists(serverPath)){
                LOGGER.info("尚未注册该 ["+serverName+"] 节点");
                return null;
            }
            List<String> nodeList = zkClient.getChildren(serverPath);
            if (nodeList.isEmpty()){
                LOGGER.info("节点列表为空");
                return null;
            }

            List<GSUrl> gsUrls = new ArrayList<GSUrl>();
            for (String node : nodeList) {
                String nodePath = serverPath + "/" + node;
                String data = zkClient.readData(nodePath);
                GSUrl gsurl = GSUrl.buildFromStr(data);
                if (null != gsurl) {
                    LOGGER.info(String.format("获取注册服务 %s %s->%s", gsurl.getIface(), gsurl.getHost(), gsurl.getPort()));
                }
                gsUrls.add(gsurl);
            }

            return gsUrls;
        }finally {
            zkClient.close();
        }

    }
}
