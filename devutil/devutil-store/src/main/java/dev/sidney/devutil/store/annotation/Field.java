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

import dev.sidney.devutil.store.enums.FieldType;

/**
 * 字段类型
 * @author 杨丰光 2015年8月20日16:18:38
 *
 */
@Documented  
@Retention(RetentionPolicy.RUNTIME)  
@Target({ ElementType.FIELD})  
@Inherited
public @interface Field {

	/**
	 * 是否需要持久化
	 * @return
	 */
	public boolean saved() default true;
	
//	/**
//	 * 是否是主键
//	 * @return
//	 */
//	public boolean primaryKey() default false;
//	
//	/**
//	 * 唯一约束名
//	 * @return
//	 */
//	public String uniqueName();
	public boolean nullable() default true;
	
	/**
	 * 字段类型
	 * @return
	 */
	public FieldType type() default FieldType.VARCHAR2;
	/**
	 * 字段长度
	 * @return
	 */
	public int size() default 256;
	
	/**
	 * 数字精度
	 * @return
	 */
	public int p() default 19;
	
	/**
	 * 小数位数
	 * @return
	 */
	public int s() default 2;
	
	/**
	 * 表字段名
	 * @return
	 */
	public String columnName() default "";
	
	/**
	 * 备注
	 * @return
	 */
	public String comment() default "";
}
