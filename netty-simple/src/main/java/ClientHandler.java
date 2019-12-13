import com.google.common.util.concurrent.SettableFuture;
import domain.RpcRequest;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Here be dragons !
 * @author: Ezio
 * 2019/12/11 12:22 下午
 *
 */
@ChannelHandler.Sharable
public class ClientHandler extends SimpleChannelInboundHandler<Object> {
    Channel channel;


    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
        channel = ctx.channel();
    }

    /**
     * 当被通知Channel是活跃的时候发送一条消息
     *
     * @param
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        // ctx.writeAndFlush(Unpooled.copiedBuffer("Netty rocks", CharsetUtil.UTF_8));
    }

    /**
     * 记录已接收消息的转储
     *
     * @param channelHandlerContext
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object object) throws Exception {
        System.out.println("1");
        System.out.println("channelRead0 Client received: " + object);
//        AbstractFuture<RpcResponse> future = requestContext.get(response.getRequestId());
//        future.set(response);
        SettableFuture settableFuture = requestContext.get("ezio");
        settableFuture.set(object);

//        requestContext.remove(response.getRequestId());
    }

    private ConcurrentHashMap<String, SettableFuture> requestContext = new ConcurrentHashMap<>(16);

    public SettableFuture sendRpcRequest() {
        RpcRequest request = new RpcRequest();
        request.setId("ezio");
        request.setClassName("com.ezio.server.api.UserService");
        request.setMethodName("getById");
        request.setParameterTypes(new String[]{"int"});
        request.setParameters(new Object[]{1});
        SettableFuture<Object> future = SettableFuture.create();
        requestContext.put(request.getId(), future);

//        AbstractFuture<RpcRequest> future = new AbstractFuture<>();
//        requestContext.put(request.getId(),future);
//
        channel.writeAndFlush(request);
//
//        return future;

        return future;
    }


    /**
     * 处理异常
     *
     * @param context
     * @param cause
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext context, Throwable cause) {
        cause.printStackTrace();
        context.close();
    }
}
