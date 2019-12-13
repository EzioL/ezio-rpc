package domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Here be dragons !
 * @author: Ezio
 * 2019/10/23 7:41 下午
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RpcRequest implements Serializable {

    private String id;

    private String className;

    private String methodName;

    private String[] parameterTypes;

    private Object[] parameters;


}
