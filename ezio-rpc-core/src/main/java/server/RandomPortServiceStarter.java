package server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.util.SocketUtils;
import registry.zk.ServiceRegistry;

/**
 * Here be dragons !
 * @author: Ezio
 * 2019/12/9 7:46 下午
 *
 */
public class RandomPortServiceStarter implements AutoCloseable, CommandLineRunner {


    @Autowired
    private ServiceRegistry serviceRegistry;
    @Autowired
    private RpcServerChannelInitializer serverChannelInitializer;

    private NioEventLoopGroup bossGroup = new NioEventLoopGroup();
    private NioEventLoopGroup workerGroup = new NioEventLoopGroup();

    public ChannelFuture start() {
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        try {
            serverBootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .childHandler(serverChannelInitializer).option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            int inetPort = SocketUtils.findAvailableTcpPort();
            ChannelFuture bindFuture = serverBootstrap.bind(inetPort).sync();
            bindFuture.addListener(future -> {
                if (future.isSuccess()) {
                    serviceRegistry.connect();
                    serviceRegistry.register(inetPort);
                } else {
                    System.exit(1);
                }
            });
            return bindFuture;
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() throws Exception {
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }

    @Override
    public void run(String... args) throws Exception {
        start();
    }
}
