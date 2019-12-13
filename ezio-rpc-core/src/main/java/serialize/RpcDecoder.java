package serialize;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.springframework.core.ResolvableType;

import java.util.List;

/**
 * Here be dragons !
 * @author: Ezio
 * 2019/12/9 6:20 下午
 *
 */
public class RpcDecoder<T> extends ByteToMessageDecoder {

    private Class<T> clazz;

    public RpcDecoder() {

        ResolvableType resolvableType = ResolvableType.forInstance(this);
        ResolvableType genericType = resolvableType.getSuperType().getGeneric(0);
        Class<?> rawClass = genericType.getRawClass();
        clazz = (Class<T>) rawClass;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        int readableLength = in.readableBytes();
        byte[] bytes = new byte[readableLength];
        in.readBytes(bytes);
        T object = FastJsonSerializer.deserialize(bytes, clazz);
        out.add(object);
    }


    public static void main(String[] args) {
        RpcDecoder<List<String>> listRpcDecoder = new RpcDecoder<List<String>>() {
        };
        System.out.println(listRpcDecoder.clazz.equals(List.class));
    }
}
