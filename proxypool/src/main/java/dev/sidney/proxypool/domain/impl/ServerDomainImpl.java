/**
 * 
 */
package dev.sidney.proxypool.domain.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import dev.sidney.devutil.store.dao.CommonDAO;
import dev.sidney.proxypool.domain.ServerDomain;
import dev.sidney.proxypool.dto.ServerDTO;
import dev.sidney.proxypool.model.Server;

/**
 * @author 杨丰光 2017年3月23日09:53:15
 *
 */
@Service
public class ServerDomainImpl implements ServerDomain {
	
	@Resource(name="proxyDao")
	private CommonDAO dao;

	/* (non-Javadoc)
	 * @see dev.sidney.proxypool.domain.BaseDomain#insert(java.lang.Object)
	 */
	public void insert(ServerDTO dto) {
		Server model = this.boToPo(dto);
		dao.insert(model);
		dto.setId(model.getId());
	}

	/* (non-Javadoc)
	 * @see dev.sidney.proxypool.domain.BaseDomain#updateById(java.lang.Object)
	 */
	public int updateById(ServerDTO t) {
		return dao.update(this.boToPo(t));
	}

	/* (non-Javadoc)
	 * @see dev.sidney.proxypool.domain.BaseDomain#deleteById(java.lang.String)
	 */
	public int deleteById(String id) {
		return dao.deleteById(Server.class, id);
	}

	private Server boToPo(ServerDTO dto) {
		Server model = null;
		if (dto != null) {
			model = new Server();
			model.setAlias(dto.getAlias());
			model.setGmtCreate(dto.getGmtCreate());
			model.setGmtLastTest(dto.getGmtLastTest());
			model.setGmtModified(dto.getGmtModified());
			model.setId(dto.getId());
			model.setIp(dto.getIp());
			model.setLocation(dto.getLocation());
			model.setPort(dto.getPort());
			model.setSpeed(dto.getSpeed());
			model.setType(dto.getType());
			model.setMemo(dto.getMemo());
		}
		return model;
	}
	
	private ServerDTO poToBo(Server model) {
		ServerDTO dto = null;
		if (model != null) {
			dto = new ServerDTO();
			dto.setAlias(model.getAlias());
			dto.setGmtCreate(model.getGmtCreate());
			dto.setGmtLastTest(model.getGmtLastTest());
			dto.setGmtModified(model.getGmtModified());
			dto.setId(model.getId());
			dto.setIp(model.getIp());
			dto.setLocation(model.getLocation());
			dto.setPort(model.getPort());
			dto.setSpeed(model.getSpeed());
			dto.setType(model.getType());
			dto.setMemo(model.getMemo());
		}
		return dto;
	}

	@Override
	public ServerDTO getById(String id) {
		Server query = new Server();
		query.setId(id);
		Server model = dao.queryForObject(query);
		return this.poToBo(model);
	}

}
