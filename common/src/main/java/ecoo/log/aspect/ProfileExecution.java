package ecoo.log.aspect;

import java.lang.annotation.*;

/**
 * @author Justin Rundle
 * @since February 2017
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Inherited
@Documented
public @interface ProfileExecution {

}