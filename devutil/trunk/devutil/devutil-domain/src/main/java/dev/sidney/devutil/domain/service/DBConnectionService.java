/**
 * 
 */
package dev.sidney.devutil.domain.service;

import java.util.List;

import dev.sidney.devutil.domain.dto.DBConnectionDTO;
import dev.sidney.devutil.domain.exception.BusinessException;

/**
 * @author 杨丰光 2015年9月18日11:21:50
 *
 */
public interface DBConnectionService {

	void add(DBConnectionDTO dto) throws BusinessException;
	
	List<DBConnectionDTO> getListByUserId(String userId);
	
	void testConnection(DBConnectionDTO dto) throws BusinessException;
}
