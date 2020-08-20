package owen.galaga.rpc.common.base;

import java.util.Arrays;

/**
 * @author Owen.Wang
 * @description: RpcRequest
 * @date 2020/5/30 18:40
 */

public class RpcRequest {

    private String requestId;
    private String className;
    private String methodName;
    private String version;
    private Class<?>[] parameterTypes;
    private Object[] parameters;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Class<?>[] getParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(Class<?>[] parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

    public Object[] getParameters() {
        return parameters;
    }

    public void setParameters(Object[] parameters) {
        this.parameters = parameters;
    }

    @Override
    public String toString() {
        return "{\"RpcRequest\":{"
                + "\"requestId\":\"" + requestId + "\""
                + ", \"className\":\"" + className + "\""
                + ", \"methodName\":\"" + methodName + "\""
                + ", \"parameterTypes\":" + Arrays.toString(parameterTypes)
                + ", \"parameters\":" + Arrays.toString(parameters)
                + "}}";
    }
}
