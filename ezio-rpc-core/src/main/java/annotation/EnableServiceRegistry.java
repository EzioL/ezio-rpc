package annotation;

import org.springframework.context.annotation.Import;
import spring.ServiceRegistryAutoConfiguration;

import java.lang.annotation.*;

/**
 * Here be dragons !
 * @author: Ezio
 * 2019/12/6 11:16 上午
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(ServiceRegistryAutoConfiguration.class)
public @interface EnableServiceRegistry {
}
