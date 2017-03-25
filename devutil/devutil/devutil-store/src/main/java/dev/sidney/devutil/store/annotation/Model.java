/**
 * 
 */
package dev.sidney.devutil.store.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 杨丰光 2015年8月20日16:16:44
 *
 */
@Documented  
@Retention(RetentionPolicy.RUNTIME)  
@Target({ ElementType.TYPE})  
@Inherited
public @interface Model {

	public String[] primaryKey() default "id";
	public String tableName();
	public String comment() default "";
	public String[] unique1() default "";
	public String[] unique2() default "";
	public String[] unique3() default "";
}
