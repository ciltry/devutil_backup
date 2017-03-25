/**
 * 
 */
package dev.sidney.devutil.domain.service.impl;

import javax.annotation.Resource;

import dev.sidney.devutil.store.dao.CommonDAO;

/**
 * 服务类的基类
 * @author 杨丰光 2015年8月27日17:44:07
 *
 */
public abstract class BaseService {

	@Resource(name="commonDao")
	protected CommonDAO dao;
}
