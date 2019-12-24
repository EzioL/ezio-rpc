package server;

import domain.RpcRequest;
import domain.RpcResponse;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import org.springframework.beans.factory.annotation.Autowired;
import serialize.RpcDecoder;
import serialize.RpcEncoder;

/**
 * Here be dragons !
 * @author: Ezio
 * 2019/12/9 6:02 下午
 *
 */

public class RpcServerChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Autowired
    private RpcServerHandler rpcServerHandler;

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline()
//                .addLast(new LengthFieldBasedFrameDecoder(65535, 0, 4, 0, 4))
//                .addLast(new RpcDecoder<RpcRequest>())
//                .addLast(new LengthFieldPrepender(4, false))
//                .addLast(new RpcEncoder<RpcResponse>)
                .addLast(new LengthFieldBasedFrameDecoder(65535, 0, 4, 0, 4))
                .addLast(new LengthFieldPrepender(4, false))
                .addLast(new RpcDecoder<RpcRequest>(){})
                .addLast(new RpcEncoder<RpcResponse>(){})
                .addLast(rpcServerHandler);


    }
}
