package server;

import domain.RpcRequest;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @creed: Here be dragons !
 * @author: Ezio
 * @Time: 2019/12/9 7:47 下午
 * @desc:
 */
public class RpcServerHandler extends SimpleChannelInboundHandler<RpcRequest> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcRequest request) throws Exception {


    }
}
