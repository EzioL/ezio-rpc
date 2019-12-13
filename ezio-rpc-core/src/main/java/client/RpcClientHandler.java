package client;

import com.google.common.util.concurrent.SettableFuture;
import domain.RpcRequest;
import domain.RpcResponse;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

import java.net.SocketAddress;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Here be dragons !
 * @author: Ezio
 * 2019/12/9 6:05 下午
 *
 */
@Slf4j
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


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcResponse msg) throws Exception {
        String requestId = msg.getRequestId();
        SettableFuture<RpcResponse> future = requestContext.get(requestId);
        Assert.notNull(future, "rpcResponseFuture for requestId " + requestId + " should not be null");
        requestContext.remove(requestId);
        future.set(msg);
        log.info("client received:{}", msg);
    }


    public SettableFuture<RpcResponse> sendRequest(RpcRequest request) {
        String requestId = request.getId();
        SettableFuture<RpcResponse> future = SettableFuture.create();
        requestContext.put(requestId, future);
        channel.writeAndFlush(request);
        log.info("client send request:{}", request);
        return future;
    }


}
