package spring;

import client.ConnectionManager;
import org.springframework.context.annotation.Bean;
import proxy.ServiceProxyHandler;
import registry.zk.ServiceDiscovery;

/**
 * @creed: Here be dragons !
 * @author: Ezio
 * @Time: 2019/12/6 6:45 下午
 * @desc:
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
