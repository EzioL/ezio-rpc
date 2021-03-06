package proxy;

import client.ConnectionManager;
import client.RpcClientHandler;
import com.google.common.util.concurrent.SettableFuture;
import domain.RpcRequest;
import domain.RpcResponse;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.ExecutionException;


/**
 * Here be dragons !
 * @author: Ezio
 * 2019/12/9 6:59 下午
 *
 */
public class ServiceProxyHandler {

    @Autowired
    private ConnectionManager connectionManager;

    public <T> T serviceProxy(String serviceName, Class<T> serviceInterface) {
        Object proxyInstance = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                new Class[]{serviceInterface}, getInvocationHandler(serviceName));
        return (T) proxyInstance;
    }

    private InvocationHandler getInvocationHandler(String serviceName) {

        return new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws ExecutionException, InterruptedException {
                if (Object.class.equals(method.getDeclaringClass())) {
                    switch (method.getName()) {
                        case "equals":
                            return proxy == args[0];
                        case "hashCode":
                            return System.identityHashCode(proxy);
                        case "toString":
                            return proxy.getClass().getName() + "@" +
                                    Integer.toHexString(System.identityHashCode(proxy)) +
                                    ", with InvocationHandler " + this;
                        default:
                            throw new IllegalStateException(String.valueOf(method));
                    }
                }

                String className = method.getDeclaringClass().getName();
                String methodName = method.getName();
                String[] parameterTypes = Arrays.stream(method.getParameterTypes()).map(Class::getName)
                        .toArray(String[]::new);

                RpcRequest request = new RpcRequest(UUID.randomUUID().toString(), className, methodName,
                        parameterTypes, args);
                RpcClientHandler rpcClientHandler = connectionManager.getRpcClientHandler(serviceName);
                SettableFuture<RpcResponse> future = rpcClientHandler.sendRequest(request);
                return future.get().getData();

            }
        };

    }

}
