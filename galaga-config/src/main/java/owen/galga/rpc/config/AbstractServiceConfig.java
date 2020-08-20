package owen.galga.rpc.config;

/**
 * @author Owen.Wang
 * @description: RPC客户端
 * @date 2020/5/30 15:03
 */

public abstract class AbstractServiceConfig {

    // 服务版本
    protected String version;

    // 服务分组
    protected String group;

    // 延迟暴露
    protected Integer delay;

    // 权重
    protected Integer weight;

    // 是否使用令牌
    protected String token;

    // 允许执行请求数
    protected Integer executes;

    // 是否注册
    protected Boolean register;

    //协议
    protected String protocol;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public Integer getDelay() {
        return delay;
    }

    public void setDelay(Integer delay) {
        this.delay = delay;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getExecutes() {
        return executes;
    }

    public void setExecutes(Integer executes) {
        this.executes = executes;
    }

    public Boolean getRegister() {
        return register;
    }

    public void setRegister(Boolean register) {
        this.register = register;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }
}
