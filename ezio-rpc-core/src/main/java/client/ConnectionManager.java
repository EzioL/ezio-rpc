package client;

import com.google.common.collect.ImmutableList;
import exception.ConnectException;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import registry.zk.ServiceDiscovery;

import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @creed: Here be dragons !
 * @author: Ezio
 * @Time: 2019/12/9 6:41 下午
 * @desc:
 */
public class ConnectionManager {

    @Autowired
    private ServiceDiscovery serviceDiscovery;
    private EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
    private ConcurrentHashMap<String, RpcClientHandler> handlers = new ConcurrentHashMap<>();


    public RpcClientHandler getRpcClientHandler(String serviceName) {
        ImmutableList<Pair<String, Integer>> service = serviceDiscovery.getService(serviceName);

        if (service.size() == 0) {
            return null;
        }
        Random random = new Random();
        int i = random.nextInt(service.size());
        Pair<String, Integer> randomServiceAddr = service.get(i);
        return connect(randomServiceAddr);
    }

    private RpcClientHandler connect(Pair<String, Integer> serviceAddr) {
        String addr = serviceAddr.getKey() + ":" + serviceAddr.getValue();
        return Optional.ofNullable(handlers.get(addr))
                .orElseGet(() -> connect0(serviceAddr, addr));
    }

    private RpcClientHandler connect0(Pair<String, Integer> serviceAddr, String addr) {
        synchronized (this) {
            RpcClientHandler handler = handlers.get(addr);
            if (handler != null) {
                return handler;
            }
            Bootstrap bootstrap = new Bootstrap();
            RpcClientChannelInitializer channelInitializer = new RpcClientChannelInitializer();
            try {
                bootstrap.group(eventLoopGroup)
                        .channel(NioSocketChannel.class)
                        .handler(channelInitializer)
                        .option(ChannelOption.SO_KEEPALIVE, true);
                ChannelFuture connectFuture = bootstrap.connect(serviceAddr.getKey(), serviceAddr.getValue()).sync();
                connectFuture.sync();
                handlers.put(addr, channelInitializer.getRpcClientHandler());
                return channelInitializer.getRpcClientHandler();
            } catch (Exception e) {
                throw new ConnectException("rpc client connect exception", e);
            }
        }
    }
}
