/**
 * 
 */
package dev.sidney.devutil.domain.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.sidney.devutil.domain.dto.UserDTO;
import dev.sidney.devutil.domain.exception.BusinessException;
import dev.sidney.devutil.domain.exception.ExceptionCodeEnum;
import dev.sidney.devutil.domain.req.RegisterUserReq;
import dev.sidney.devutil.domain.service.UserService;
import dev.sidney.devutil.store.dao.CommonDAO;
import dev.sidney.devutil.store.model.system.User;

/**
 * 
 * @author 杨丰光 2015年8月26日18:27:16
 *
 */
@Service
public class UserServiceImpl extends BaseService implements UserService {
	
	@Resource(name="commonDao")
	private CommonDAO dao;

	@Transactional("transactionManager")
	public void register(RegisterUserReq registerUserReq) throws BusinessException {
		
		if (usernameExists(registerUserReq.getUsername().trim())) {
			throw new BusinessException(ExceptionCodeEnum.USERNAME_ALREADY_EXISTS);
		}
		
		User user = new User();
		user.setUsername(registerUserReq.getUsername());
		user.setNickname(registerUserReq.getNickname());
		user.setPassword(registerUserReq.getPassword());
		user.setGmtCreate(new Date());
		user.setGmtModified(user.getGmtCreate());
		dao.insert(user);
	}
	
	
	private boolean usernameExists(String username) {
		User user = new User();
		user.setUsername(username);
		return dao.queryForObject(user) != null;
	}


	@Override
	public UserDTO getUser(String username) throws BusinessException {
		UserDTO dto = null;
		User user = new User();
		user.setUsername(username);
		try {
			user = dao.queryForObject(user);
		} catch (CannotGetJdbcConnectionException e) {
			throw new BusinessException(ExceptionCodeEnum.FAILED_TO_CONNECT_DB, e);
		} catch (Exception e) {
			throw new BusinessException(ExceptionCodeEnum.FAILED_TO_ACCESS_DATA, e);
		}
		if (user != null) {
			dto = new UserDTO();
			dto.setUsername(user.getUsername());
			dto.setNickname(user.getNickname());
			dto.setPassword(user.getPassword());
			dto.setId(user.getId());
		}
		
		return dto;
	}
	
}
