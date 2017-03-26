/**
 * 
 */
package dev.sidney.proxypool.domain.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import dev.sidney.devutil.store.dao.CommonDAO;
import dev.sidney.devutil.store.domain.BaseDomain;
import dev.sidney.proxypool.domain.ServerDomain;
import dev.sidney.proxypool.dto.ServerDTO;

/**
 * @author 杨丰光 2017年3月23日09:53:15
 *
 */
@Service
public class ServerDomainImpl extends BaseDomain<ServerDTO> implements ServerDomain {
	
	@Resource(name="proxyDao")
	private CommonDAO dao;


	@Override
	protected CommonDAO getDAO() {
		return dao;
	}

}
