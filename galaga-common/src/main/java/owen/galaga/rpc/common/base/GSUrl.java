package owen.galaga.rpc.common.base;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Owen.Wang
 * @description: TODO
 * @date 2020/6/6 16:07
 */
public class GSUrl implements Serializable {

    private String host;
    private int port;
    private int pid;
    private String iface;
    private String alias;
    private String protocol;
    private Map<String, String> attrs;
    private int timeout;
    private boolean random;
    private long stTime;
    private String insKey;
    private long dataVersion;
    private String registryAddress;
    private Map<String, String> parameters;


    public GSUrl() {}

    private GSUrl(String host, int port, String iface, String alias, String registryAddress) {
        this.host = host;
        this.port = port;
        this.iface = iface;
        this.alias = alias;
        this.registryAddress = registryAddress;
    }

    public static GSUrl build(String host, int port, String iface, String alias, String registryAddress) {
        return new GSUrl(host, port, iface, alias, registryAddress);
    }

    public static GSUrl buildFromStr(String data) {
        if (null == data || "".equals(data)) {
            return null;
        }
        return JSONObject.parseObject(data, GSUrl.class);
    }

    public String getAddress() {
        return port <= 0 ? host : host + ":" + port;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getIface() {
        return iface;
    }

    public void setIface(String iface) {
        this.iface = iface;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public Map<String, String> getAttrs() {
        return attrs;
    }

    public void setAttrs(Map<String, String> attrs) {
        this.attrs = attrs;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public boolean isRandom() {
        return random;
    }

    public void setRandom(boolean random) {
        this.random = random;
    }

    public long getStTime() {
        return stTime;
    }

    public void setStTime(long stTime) {
        this.stTime = stTime;
    }

    public String getInsKey() {
        return insKey;
    }

    public void setInsKey(String insKey) {
        this.insKey = insKey;
    }

    public long getDataVersion() {
        return dataVersion;
    }

    public void setDataVersion(long dataVersion) {
        this.dataVersion = dataVersion;
    }

    public String getRegistryAddress() {
        return registryAddress;
    }

    public void setRegistryAddress(String registryAddress) {
        this.registryAddress = registryAddress;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public String cacheKey() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.iface).append(this.alias).append(this.host).append(this.port);
        buildParameters(sb, true, this.parameters);
        return sb.toString();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"host\":\"")
                .append(host).append('\"');
        sb.append(",\"port\":")
                .append(port);
        sb.append(",\"pid\":")
                .append(pid);
        sb.append(",\"iface\":\"")
                .append(iface).append('\"');
        sb.append(",\"alias\":\"")
                .append(alias).append('\"');
        sb.append(",\"protocol\":\"")
                .append(protocol).append('\"');
        sb.append(",\"attrs\":")
                .append(attrs);
        sb.append(",\"timeout\":")
                .append(timeout);
        sb.append(",\"random\":")
                .append(random);
        sb.append(",\"stTime\":")
                .append(stTime);
        sb.append(",\"insKey\":\"")
                .append(insKey).append('\"');
        sb.append(",\"dataVersion\":")
                .append(dataVersion);
        sb.append(",\"registryAddress\":\"")
                .append(registryAddress).append('\"');
        sb.append(",\"parameters\":")
                .append(parameters);
        sb.append('}');
        return sb.toString();
    }

    private void buildParameters(StringBuilder buf, boolean concat, Map<String, String> parameters) {
        if (getParameters() != null && getParameters().size() > 0) {
            List<String> includes = (parameters == null || parameters.keySet().size() == 0 ? null : new ArrayList<String>(parameters.keySet()));
            boolean first = true;
            for (Map.Entry<String, String> entry : new TreeMap<String, String>(getParameters()).entrySet()) {
                if (entry.getKey() != null && entry.getKey().length() > 0
                        && (includes == null || includes.contains(entry.getKey()))) {
                    if (first) {
                        if (concat) {
                            buf.append("?");
                        }
                        first = false;
                    } else {
                        buf.append("&");
                    }
                    buf.append(entry.getKey());
                    buf.append("=");
                    buf.append(entry.getValue() == null ? "" : entry.getValue().trim());
                }
            }
        }
    }

}
