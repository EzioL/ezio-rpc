package serialize;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import domain.RpcRequest;

import java.util.UUID;

/**
 * @creed: Here be dragons !
 * @author: Ezio
 * @Time: 2019-10-24 19:07
 * @desc:
 */
public class FastJsonSerializer {


    public static <T> byte[] serialize(T t) {

        return JSON.toJSONBytes(t, SerializerFeature.WriteClassName);
    }

    public static <T> T deserialize(byte[] data, Class<T> clazz) {
        return JSON.parseObject(data, clazz);
    }

    public static void main(String[] args) {
        RpcRequest request = getRequest();
        byte[] bytes = FastJsonSerializer.serialize(request);
        RpcRequest deserialize = FastJsonSerializer.deserialize(bytes, request.getClass());
        System.out.println(request.equals(deserialize));
    }

    public static RpcRequest getRequest() {
        RpcRequest request = new RpcRequest();
        request.setId(UUID.randomUUID().toString());
        request.setClassName("com.ezio.service.UserService");
        request.setMethodName("get");
        request.setParameters(new Object[]{24L});
        request.setParameterTypes(new Class[]{Long.class});
        return request;
    }


}
