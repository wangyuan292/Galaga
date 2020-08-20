package owen.galaga.rpc.common.base;

/**
 * @author Owen.Wang
 * @description:
 * @date 2020/5/30 18:44
 */
public class RpcResponse {

    private String requestId;
    private Throwable error;
    private Object result;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public Throwable getError() {
        return error;
    }

    public void setError(Throwable error) {
        this.error = error;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "{\"RpcResponse\":{"
                + "\"requestId\":\"" + requestId + "\""
                + ", \"error\":" + error
                + ", \"result\":" + result
                + "}}";
    }
}
