/**
 * 
 */
package dev.sidney.proxypool.domain;

/**
 * @author 杨丰光 2017年3月23日09:50:35
 *
 */
public interface BaseDomain<T> {
	
	void insert(T dto);
	int updateById(T dto);
	int deleteById(String id);
	T getById(String id);
}
