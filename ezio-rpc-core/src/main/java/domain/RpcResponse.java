package domain;

import lombok.Data;

/**
 * @creed: Here be dragons !
 * @author: Ezio
 * @Time: 2019/10/24 9:44 上午
 * @desc:
 */
@Data
public class RpcResponse {

    private String requestId;
    private int code;
    private String msg;
    private Object data;

    public static RpcResponse ok(String requestId, Object data) {
        RpcResponse rpcResponse = new RpcResponse();
        rpcResponse.setRequestId(requestId);
        rpcResponse.setCode(200);
        rpcResponse.setData(data);
        return rpcResponse;
    }
}
