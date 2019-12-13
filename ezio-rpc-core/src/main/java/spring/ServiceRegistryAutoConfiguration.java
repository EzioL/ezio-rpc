package spring;

import org.springframework.context.annotation.Bean;
import registry.zk.ServiceRegistry;
import server.RandomPortServiceStarter;
import server.RpcServerChannelInitializer;
import server.RpcServerHandler;
import server.ServiceProvider;

/**
 * Here be dragons !
 * @author: Ezio
 * 2019/12/6 6:45 下午
 *
 */

public class ServiceRegistryAutoConfiguration {

    @Bean
    public ServiceRegistry serviceRegistry() { return new ServiceRegistry(); }

    @Bean
    public ServiceProvider serviceProvider() {
        return new ServiceProvider();
    }

    @Bean
    public RpcServerHandler rpcServerHandler() {
        return new RpcServerHandler();
    }

    @Bean
    public RpcServerChannelInitializer serverChannelInitializer() {
        return new RpcServerChannelInitializer();
    }

    @Bean
    public RandomPortServiceStarter serviceStarter() {
        return new RandomPortServiceStarter();
    }
}
