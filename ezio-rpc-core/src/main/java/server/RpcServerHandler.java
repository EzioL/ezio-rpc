package server;

import domain.RpcRequest;
import domain.RpcResponse;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @creed: Here be dragons !
 * @author: Ezio
 * @Time: 2019/12/9 7:47 下午
 * @desc:
 */
@Sharable
public class RpcServerHandler extends SimpleChannelInboundHandler<RpcRequest> {

    @Autowired
    private ServiceProvider serviceProvider;


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcRequest request) throws Exception {
        // TODO: Ezio 2019/12/10  可以搞个线程池
        RpcResponse response = (RpcResponse) serviceProvider.invoke(request);
        ctx.writeAndFlush(response);
    }
}
