import domain.RpcRequest;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;


/**
 * @creed: Here be dragons !
 * @author: Ezio
 * @Time: 2019/12/11 12:12 下午
 * @desc:
 */
@Slf4j
public class Client {


    public static void main(String[] args) throws InterruptedException {


        EventLoopGroup group = new NioEventLoopGroup();
        try {
            /**
             * 创建Bootstrap
             * 指定EventLoopGroup
             * 适用于NIO传输的Channel类型
             * 创建Channel时，向ChannelPipeline中添加一个EchoClientHandler实例
             */
            Bootstrap b = new Bootstrap();
            ClientHandler clientHandler = new ClientHandler();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .remoteAddress(new InetSocketAddress("localhost", 5600))
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline()
//                                    .addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,0, 4, 0, 4))
//                                    .addLast(new LengthFieldPrepender(4))
                                    .addLast(new ObjectEncoder())
                                    .addLast(new ObjectDecoder(Integer.MAX_VALUE,
                                            ClassResolvers.weakCachingConcurrentResolver(null)))
                                    .addLast(clientHandler);
                        }
                    });
            // 连接远程节点直到连接完成
            ChannelFuture future = b.connect().sync();

            future.channel().writeAndFlush(new RpcRequest( "ezio",null,null,null,null)).sync();
//            future.channel().writeAndFlush(Unpooled.copiedBuffer("ezio", CharsetUtil.UTF_8));
//            future.channel().writeAndFlush("ezio");
//            future.channel().writeAndFlush(10);
//            clientHandler.sendRpcRequest();
            // 阻塞直到Channel关闭
            future.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully().sync();
        }

    }


}
