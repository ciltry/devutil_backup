/**
 * 
 */
package dev.sidney.devutil.domain.logic;

/**
 * @author 杨丰光 2015年8月19日17:54:38
 *
 */
public interface LogicDB {

	/**
	 * 是否允许访问DB
	 * @param context
	 * @return
	 */
	boolean accessEntitled(Context context);
	
}
