package client;

import domain.RpcRequest;
import domain.RpcResponse;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import serialize.RpcDecoder;
import serialize.RpcEncoder;

/**
 * @creed: Here be dragons !
 * @author: Ezio
 * @Time: 2019/12/9 6:02 下午
 * @desc:
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
                .addLast(new RpcDecoder<RpcResponse>() {
                })
                .addLast(new LengthFieldPrepender(4, false))
                .addLast(new RpcEncoder<RpcRequest>() {
                })
                .addLast(rpcClientHandler);


    }


}
