package annotation;

import org.springframework.context.annotation.Import;
import spring.ServiceDiscoveryAutoConfiguration;

import java.lang.annotation.*;

/**
 * Here be dragons !
 * @author: Ezio
 * 2019/12/6 11:13 上午
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(value = ServiceDiscoveryAutoConfiguration.class)
public @interface EnableServiceDiscovery {
}
