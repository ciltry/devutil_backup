/**
 * 
 */
package dev.sidney.devutil.domain.logic;

/**
 * 系统逻辑
 * @author 杨丰光 2015年8月19日18:08:24
 *
 */
public interface LogicSystem {

	/**
	 * 系统是否需要登录
	 * @return
	 */
	boolean needLogin(Context context);
	
	
}
