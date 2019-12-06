package spring;

import org.springframework.context.annotation.Bean;
import registry.zk.ServiceRegistry;

/**
 * @creed: Here be dragons !
 * @author: Ezio
 * @Time: 2019/12/6 6:45 下午
 * @desc:
 */

public class ServiceRegistryAutoConfiguration {

    @Bean
    public ServiceRegistry serviceRegistry() {
        return new ServiceRegistry();
    }

}
