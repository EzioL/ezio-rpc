package domain;

import lombok.Data;

/**
 * @creed: Here be dragons !
 * @author: Ezio
 * @Time: 2019/10/23 7:41 下午
 * @desc:
 */
@Data
public class RpcRequest {

    private String id;

    private String className;

    private String methodName;

    private Class<?>[] parameterTypes;

    private Object[] parameters;


}
