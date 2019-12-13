package client;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

/**
 * Here be dragons !
 * @author: Ezio
 * 2019/12/9 6:02 下午
 *
 */
public class RpcClientChannelInitializer extends ChannelInitializer<SocketChannel> {

    private RpcClientHandler rpcClientHandler = new RpcClientHandler();


    public RpcClientHandler getRpcClientHandler() {
        return rpcClientHandler;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline()
                .addLast(new LengthFieldBasedFrameDecoder(65535, 0, 4, 0, 4))
                .addLast(new LengthFieldPrepender(4, false))
                .addLast(new ObjectEncoder())
                .addLast(new ObjectDecoder(Integer.MAX_VALUE,
                        ClassResolvers.weakCachingConcurrentResolver(null)))
                .addLast(rpcClientHandler);


    }


}
