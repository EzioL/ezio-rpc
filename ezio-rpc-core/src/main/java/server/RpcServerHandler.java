package server;

import domain.RpcRequest;
import domain.RpcResponse;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @creed: Here be dragons !
 * @author: Ezio
 * @Time: 2019/12/9 7:47 下午
 * @desc:
 */
@Slf4j
@ChannelHandler.Sharable
public class RpcServerHandler extends ChannelInboundHandlerAdapter {

    @Autowired
    private ServiceProvider serviceProvider;


//    @Override
//    protected void channelRead0(ChannelHandlerContext ctx, RpcRequest request) throws Exception {
//        // TODO: Ezio 2019/12/10  可以搞个线程池
//        RpcResponse response = (RpcResponse) serviceProvider.invoke(request);
//        ctx.writeAndFlush(response);
//    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("server received: {}", msg);
        RpcRequest request = (RpcRequest) msg;
        Object invoke = serviceProvider.invoke(request);
        RpcResponse response = RpcResponse.success(request.getId(),invoke );
        RpcResponse response1 = new RpcResponse();
        response1.setCode(200);
        response1.setData("hahahha");

        ctx.writeAndFlush(response1);

    }
}
