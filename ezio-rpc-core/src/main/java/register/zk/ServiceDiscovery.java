package register.zk;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import lombok.Data;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @creed: Here be dragons !
 * @author: Ezio
 * @Time: 2019/12/6 5:33 下午
 * @desc:
 */
@Data
public class ServiceDiscovery {

    private String zkAddress;
    private CuratorFramework client;
    private ConcurrentHashMap<String, PathChildrenCache> servicesMap = new ConcurrentHashMap<String, PathChildrenCache>();


    public synchronized void connect() {
        CuratorFramework client = CuratorFrameworkFactory.newClient(this.zkAddress, 15 * 1000,
                5000, new ExponentialBackoffRetry(1000, 3));
        client.start();
        this.client = client;
    }

    public ImmutableList<Pair<String, Integer>> getService(String serviceName) {

        List<Pair<String, Integer>> service = Optional.ofNullable(getPathChildrenCache(serviceName))
                .map(pathChildrenCache -> {
                    return pathChildrenCache.getCurrentData().stream().map(childData -> {
                        byte[] data = childData.getData();
                        String strData = new String(data, StandardCharsets.UTF_8);
                        String[] split = strData.split(":");
                        return Pair.of(split[0], Integer.parseInt(split[1]));
                    }).collect(Collectors.toList());
                })
                .orElse(Lists.newArrayList());

        return ImmutableList.copyOf(service);
    }

    private PathChildrenCache getPathChildrenCache(String serviceName) {
        return Optional.ofNullable(servicesMap.get(serviceName)).orElseGet(() -> {
            PathChildrenCache pathChildrenCache = new PathChildrenCache(this.client,
                    ServiceRegistryManager.getServicePath(serviceName), true);
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


}
