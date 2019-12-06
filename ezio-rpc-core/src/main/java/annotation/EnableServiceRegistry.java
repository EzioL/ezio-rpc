package annotation;

import java.lang.annotation.*;

/**
 * @creed: Here be dragons !
 * @author: Ezio
 * @Time: 2019/12/6 11:16 上午
 * @desc:
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EnableServiceRegistry {
}
