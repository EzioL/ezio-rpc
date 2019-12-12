import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @creed: Here be dragons !
 * @author: Ezio
 * @Time: 2019/12/11 12:22 下午
 * @desc:
 */
@ChannelHandler.Sharable
public class ServerHandler extends ChannelInboundHandlerAdapter {
    /**
     * 每个传入的消息都要调用
     * @param context
     * @param msg
     */
    @Override
    public void channelRead(ChannelHandlerContext context, Object msg) {

        // 打印消息
        System.out.println("server received: " + msg);
        // 将接收到的消息写给发送者, 而冲刷出站消息
        context.writeAndFlush(msg);
    }

    /**
     * 在读取操作期间，有异常抛出时会调用
     * @param context
     * @param couse
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext context, Throwable couse) {
        couse.printStackTrace();
        context.close();
    }
}
