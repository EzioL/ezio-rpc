package spring;

import client.ConnectionManager;
import org.springframework.context.annotation.Bean;
import proxy.ServiceProxyHandler;
import registry.zk.ServiceDiscovery;

/**
 * Here be dragons !
 * @author: Ezio
 * 2019/12/6 6:45 下午
 *
 */

public class ServiceDiscoveryAutoConfiguration {

    @Bean
    public ServiceDiscovery serviceDiscovery() {
        return new ServiceDiscovery();
    }


    @Bean
    public ServiceProxyHandler serviceProxyHandler() {
        return new ServiceProxyHandler();
    }

    @Bean
    public ConnectionManager connectionManager() {
        return new ConnectionManager();
    }


}
