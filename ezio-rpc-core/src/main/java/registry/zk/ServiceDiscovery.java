package registry.zk;

import com.google.common.collect.ImmutableList;
import lombok.Data;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @creed: Here be dragons !
 * @author: Ezio
 * @Time: 2019/12/6 5:33 下午
 * @desc:
 */
@Data
@ConfigurationProperties("service.discovery")
public class ServiceDiscovery {

    private String zkAddress;
    private CuratorFramework client;
    private ConcurrentHashMap<String, PathChildrenCache> servicesMap = new ConcurrentHashMap<String, PathChildrenCache>();

    public synchronized void connect() {
        this.client = CuratorFrameworkFactory.newClient(this.zkAddress, 15 * 1000,
                5000, new ExponentialBackoffRetry(1000, 3));
        this.client.start();
    }

    public ImmutableList<Pair<String, Integer>> getService(String serviceName) {

        if (StringUtils.isBlank(serviceName)) {
            return ImmutableList.of();
        }
        try {
            this.connect();
            byte[] bytes = this.client.getData().forPath(ServiceRegistryConstant.getServicePath(serviceName));
            String strData = new String(bytes, StandardCharsets.UTF_8);
            String[] split = strData.split(":");

        } catch (Exception e) {
            e.printStackTrace();
        }

        return ImmutableList.of();

//        List<Pair<String, Integer>> service = Optional.ofNullable(getPathChildrenCache(serviceName))
//                .map(pathChildrenCache -> {
//                    return pathChildrenCache.getCurrentData().stream().map(childData -> {
//                        byte[] data = childData.getData();
//                        String strData = new String(data, StandardCharsets.UTF_8);
//                        String[] split = strData.split(":");
//                        return Pair.of(split[0], Integer.parseInt(split[1]));
//                    }).collect(Collectors.toList());
//                })
//                .orElse(Lists.newArrayList());
//
//        return ImmutableList.copyOf(service);
    }

    // TODO: Ezio 2019/12/9
    private PathChildrenCache getPathChildrenCache(String serviceName) {
        return Optional.ofNullable(servicesMap.get(serviceName)).orElseGet(() -> {
            PathChildrenCache pathChildrenCache = new PathChildrenCache(this.client,
                    ServiceRegistryConstant.getServicePath(serviceName), true);
            try {
                pathChildrenCache.start(PathChildrenCache.StartMode.BUILD_INITIAL_CACHE);
                servicesMap.put(serviceName, pathChildrenCache);
                return pathChildrenCache;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        });
    }


    @SneakyThrows
    public static void main(String[] args) {
//        ServiceRegistry serviceRegistry = new ServiceRegistry();
//        serviceRegistry.setServiceName("ezio111");
//        serviceRegistry.setZkAddress("localhost:2181");
//        serviceRegistry.connect();
//        serviceRegistry.register(9000);
//        Thread.sleep(2000);

        ServiceDiscovery serviceDiscovery = new ServiceDiscovery();
        serviceDiscovery.setZkAddress("localhost:2181");
        serviceDiscovery.connect();
        ImmutableList<Pair<String, Integer>> test = serviceDiscovery.getService("server-service");
        System.out.println(test);


    }

}
