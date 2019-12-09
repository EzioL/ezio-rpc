package client;

import com.google.common.util.concurrent.SettableFuture;
import domain.RpcRequest;
import domain.RpcResponse;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.net.SocketAddress;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @creed: Here be dragons !
 * @author: Ezio
 * @Time: 2019/12/9 6:05 下午
 * @desc:
 */
public class RpcClientHandler extends SimpleChannelInboundHandler<RpcResponse> {

    private Channel channel;
    private SocketAddress serverAddress;
    private ConcurrentHashMap<String, SettableFuture<RpcResponse>> requestContext = new ConcurrentHashMap<>(16);

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
        channel = ctx.channel();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        serverAddress = ctx.channel().remoteAddress();
    }

    public SocketAddress getServerAddress() {
        return serverAddress;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcResponse msg) throws Exception {
        String requestId = msg.getRequestId();
        SettableFuture<RpcResponse> rpcResponseFuture = requestContext.get(requestId);
        requestContext.remove(requestId);
        rpcResponseFuture.set(msg);

    }


    public SettableFuture<RpcResponse> sendRequest(RpcRequest request) {
        String requestId = request.getId();
        SettableFuture<RpcResponse> future = SettableFuture.create();
        requestContext.put(requestId, future);
        channel.writeAndFlush(request);
        return future;
    }


}
