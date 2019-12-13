package registry.zk;

/**
 * Here be dragons !
 * @author: Ezio
 * 2019/12/6 5:00 下午
 *
 */
public class ServiceRegistryConstant {


    public static final String CHARSET_NAME = "utf-8";

    public static final String BASE_PATH = "/service/";

    public static String getServicePath(String serviceName) {
        return BASE_PATH + serviceName ;
    }
}
