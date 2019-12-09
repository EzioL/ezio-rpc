package server;

import annotation.RpcService;
import domain.RpcRequest;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @creed: Here be dragons !
 * @author: Ezio
 * @Time: 2019/12/9 7:49 下午
 * @desc:
 */
public class ServiceProvider implements ApplicationContextAware {

    private ApplicationContext context;

    private Map<String, Object> classNameToServiceMap = new ConcurrentHashMap<>();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
        Map<String, Object> rpcServiceBeanMap = context.getBeansWithAnnotation(RpcService.class);
        if (rpcServiceBeanMap.isEmpty()) {
            return;
        }
        List<Pair<Class, Object>> services = rpcServiceBeanMap.values().stream().flatMap(service -> {
            RpcService annotation = AnnotationUtils.findAnnotation(service.getClass(), RpcService.class);
            Class[] serviceInterfaceNames = annotation.value();
            return Arrays.stream(serviceInterfaceNames).map(interfaceName -> Pair.of(interfaceName, service));
        }).collect(Collectors.toList());

        for (Pair<Class, Object> service : services) {
            classNameToServiceMap.put(service.getKey().getName(), service.getValue());
        }
    }


    public Object invoke(RpcRequest request) {
        String className = request.getClassName();

        Object service = classNameToServiceMap.get(className);

        Assert.notNull(service, className + "service not exist");

        String[] parameterTypes = request.getParameterTypes();
        Class[] parameterClasses = Arrays.stream(parameterTypes).map(typeStr -> {
            try {
                return ClassUtils.forName(typeStr, ClassUtils.getDefaultClassLoader());
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }).toArray(Class[]::new);
        Method method = ClassUtils.getMethod(service.getClass(), request.getMethodName(), parameterClasses);
        Assert.notNull(method, request.getMethodName() + " method for service: " + className + " should not be null");
        Object result = null;
        try {
            result = method.invoke(service, request.getParameters());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return result;
    }
}
