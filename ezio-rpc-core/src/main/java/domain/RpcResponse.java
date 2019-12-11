package domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @creed: Here be dragons !
 * @author: Ezio
 * @Time: 2019/10/24 9:44 上午
 * @desc:
 */
@Data
public class RpcResponse implements Serializable {

    public static final int SUCCESS_CODE = 200;


    private String requestId;
    private int code;
    private String msg;
    private Object data;

    public static RpcResponse success(String requestId, Object data) {
        RpcResponse rpcResponse = new RpcResponse();
        rpcResponse.setRequestId(requestId);
        rpcResponse.setCode(SUCCESS_CODE);
        rpcResponse.setData(data);
        return rpcResponse;
    }

    public boolean isSuccess() {
        return this.code == SUCCESS_CODE;
    }
}
