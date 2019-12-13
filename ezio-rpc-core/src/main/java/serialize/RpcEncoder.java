package serialize;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Here be dragons !
 * @author: Ezio
 * 2019/12/9 6:20 下午
 *
 */
public class RpcEncoder<T> extends MessageToByteEncoder<T> {
    @Override
    protected void encode(ChannelHandlerContext ctx, T msg, ByteBuf out) throws Exception {
        byte[] bytes = FastJsonSerializer.serialize(msg);
        out.writeBytes(bytes);
    }
}
