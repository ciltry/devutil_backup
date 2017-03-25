/**
 * 
 */
package dev.sidney.devutil.store.domain;

import dev.sidney.devutil.store.dto.BaseDTO;

/**
 * @author 杨丰光 2017年3月23日16:11:59
 *
 */
public interface AbstractDomain <T extends BaseDTO<?>> {

	void insert(T dto);
	T queryById(String id);
	T query(T query);
	T peek(T dto);
	int updateById(T dto);
}
