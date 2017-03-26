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
 * 字段映射
 * @author 杨丰光 2017年3月26日10:53:04
 *
 */
@Documented  
@Retention(RetentionPolicy.RUNTIME)  
@Target({ ElementType.FIELD})  
@Inherited
public @interface FieldMapping {
	public String value();
}
