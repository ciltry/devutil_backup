/**
 * 
 */
package dev.sidney.devutil.domain.service;

import dev.sidney.devutil.domain.dto.UserDTO;
import dev.sidney.devutil.domain.exception.BusinessException;
import dev.sidney.devutil.domain.req.RegisterUserReq;

/**
 * @author 杨丰光 2015年8月26日18:18:24
 *
 */
public interface UserService {

	
	void register(RegisterUserReq registerUserReq) throws BusinessException;
	
	UserDTO getUser(String username) throws BusinessException;
}
