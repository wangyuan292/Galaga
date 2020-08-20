package owen.galga.rpc.config;

/**
 * @author Owen.Wang
 * @description: RPC客户端
 * @date 2020/5/30 14:30
 */

public class RegistryConfig extends AbstractServiceConfig {

    private String registryAddress;

    private Integer connectTimeout;

    public String getRegistryAddress() {
        return registryAddress;
    }

    public void setRegistryAddress(String registryAddress) {
        this.registryAddress = registryAddress;
    }

    public Integer getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(Integer connectTimeout) {
        this.connectTimeout = connectTimeout;
    }
}
