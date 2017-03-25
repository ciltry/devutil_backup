/**
 * 
 */
package dev.sidney.devutil.domain.service.impl;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import dev.sidney.devutil.domain.exception.BusinessException;
import dev.sidney.devutil.domain.req.RegisterUserReq;
import dev.sidney.devutil.domain.service.UserService;

/**
 * @author 杨丰光 2015年8月27日11:38:55
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/context/*.xml" })
public class UserServiceImplTest {

	@Resource
	private UserService userService;
	
	
	@Test
	public void testRegister() {
		RegisterUserReq req = new RegisterUserReq();
		req.setNickname("亚飞金额");
		req.setUsername("ciltry");
		req.setPassword("fawe53456");
		try {
			userService.register(req);
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
