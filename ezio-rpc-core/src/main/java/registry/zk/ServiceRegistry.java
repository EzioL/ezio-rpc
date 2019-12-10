package registry.zk;

import exception.RegistryException;
import lombok.Data;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.net.InetAddress;
import java.util.List;

/**
 * @creed: Here be dragons !
 * @author: Ezio
 * @Time: 2019/12/6 4:58 下午
 * @desc:
 */
@Data
@ConfigurationProperties("service.registry")
public class ServiceRegistry {
    private String zkAddress;
    private String serviceName;
    private CuratorFramework client;


    public synchronized void connect() {
        this.client = CuratorFrameworkFactory.newClient(this.zkAddress, 15 * 1000,
                5000, new ExponentialBackoffRetry(1000, 3));
        this.client.start();

    }

    public void register(int port) {
        try {
            String hostName = InetAddress.getLocalHost().getHostName();
            String serviceAddress = hostName + ":" + port;
            this.client.create().creatingParentsIfNeeded()
                    .withMode(CreateMode.EPHEMERAL_SEQUENTIAL)
                    .forPath(ServiceRegistryConstant.getServicePath(this.serviceName) + "/server",
                            serviceAddress.getBytes(ServiceRegistryConstant.CHARSET_NAME));
        } catch (Exception e) {
            throw new RegistryException("register exception", e);
        }
    }


    public static void main(String[] args) {

        CuratorFramework client = CuratorFrameworkFactory.newClient("localhost:2181", 15 * 1000, 5000,
                new ExponentialBackoffRetry(1000, 3));

        client.start();
        try {
            String hostName = "127.0.0.1";
            String serviceAddress = hostName + ":" + 9992;

            String path = ServiceRegistryConstant.getServicePath("ezio1");
            byte[] bytes = serviceAddress.getBytes("UTF-8");
            String s = client.create().creatingParentsIfNeeded()
                    .withMode(CreateMode.EPHEMERAL_SEQUENTIAL)
                    .forPath(path + "/server", "127.0.0.1:9101".getBytes());
            System.out.println(s);
            String s2 = client.create().creatingParentsIfNeeded()
                    .withMode(CreateMode.EPHEMERAL_SEQUENTIAL)
                    .forPath(path + "/server", "127.0.0.1:9102".getBytes());
            System.out.println(s2);
//            client.setData()
//                    .forPath(path, bytes);
            List<String> strings = client.getChildren().forPath(path);
            System.out.println(strings);
            strings.stream().map(e -> {
                try {
                    return new String(client.getData().forPath(path + "/" + e));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                return null;
            }).forEach(System.out::println);

//            byte[] bytes1 = client.getData().forPath(path);
//            System.out.println(new String(bytes1));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
