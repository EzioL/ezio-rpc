package client;

import domain.RpcRequest;
import domain.RpcResponse;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.util.Assert;

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
    private ConcurrentHashMap<String, RpcResponse> requestContext = new ConcurrentHashMap<>(16);

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


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcResponse msg) throws Exception {
        String requestId = msg.getRequestId();
        RpcResponse rpcResponse = requestContext.get(requestId);
        Assert.notNull(rpcResponse, "rpcResponseFuture for requestId " + requestId + " should not be null");
        requestContext.remove(requestId);
        rpcResponse = msg;

    }


    public RpcResponse sendRequest(RpcRequest request) {
        String requestId = request.getId();
        RpcResponse rpcResponse = new RpcResponse();
        requestContext.put(requestId, rpcResponse);
        channel.writeAndFlush(request);
        return rpcResponse;
    }


}
