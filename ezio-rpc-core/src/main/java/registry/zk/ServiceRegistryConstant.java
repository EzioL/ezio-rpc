package registry.zk;

/**
 * @creed: Here be dragons !
 * @author: Ezio
 * @Time: 2019/12/6 5:00 下午
 * @desc:
 */
public class ServiceRegistryConstant {


    public static final String CHARSET_NAME = "utf-8";

    public static final String BASE_PATH = "/service/";

    public static String getServicePath(String serviceName) {
        return BASE_PATH + serviceName;
    }
}
