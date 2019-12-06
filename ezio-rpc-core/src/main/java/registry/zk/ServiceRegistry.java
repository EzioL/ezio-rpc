package registry.zk;

import lombok.Data;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

import java.net.InetAddress;

/**
 * @creed: Here be dragons !
 * @author: Ezio
 * @Time: 2019/12/6 4:58 下午
 * @desc:
 */
@Data
public class ServiceRegistry {
    private String zkAddress;
    private String serviceName;
    private CuratorFramework client;


    public synchronized void connect() {
        CuratorFramework client = CuratorFrameworkFactory.newClient(this.zkAddress, 15 * 1000,
                5000, new ExponentialBackoffRetry(1000, 3));
        client.start();
        this.client = client;
    }

    public void register(int port) {
        try {
            String hostName = InetAddress.getLocalHost().getHostName();
            String serviceAddress = hostName + ":" + port;
            this.client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath(
                    ServiceRegistryManager.getServicePath(this.serviceName) + "/server", serviceAddress.getBytes());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public static void main(String[] args) {

        CuratorFramework client = CuratorFrameworkFactory.newClient("localhost:2181", 15 * 1000, 5000,
                new ExponentialBackoffRetry(1000, 3));

        client.start();
        try {
            String hostName = InetAddress.getLocalHost().getHostName();
            String serviceAddress = hostName + ":" + 9999;
            client.create()
                    .creatingParentsIfNeeded()
                    .withMode(CreateMode.EPHEMERAL_SEQUENTIAL)
                    .forPath(
                            ServiceRegistryManager.getServicePath("test") + "/server", serviceAddress.getBytes());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
