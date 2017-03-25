/**
 * 
 */
package dev.sidney.devutil.domain.service.impl;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import dev.sidney.devutil.domain.dto.DBConnectionDTO;
import dev.sidney.devutil.domain.exception.BusinessException;
import dev.sidney.devutil.domain.service.DBConnectionService;
import dev.sidney.devutil.store.dao.CommonDAO;

/**
 * @author 杨丰光 2015年9月18日11:32:31
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/context/*.xml" })
public class DBConnectionServiceImplTest {

	@Resource
	private DBConnectionService DBConnectionService;
	
	@Resource(name="commonDao")
	private CommonDAO dao;
	
	@Test
	public void testAdd() throws BusinessException {
		dao.init();
		DBConnectionDTO dto = new DBConnectionDTO();
		dto.setCharset("utf-8");
		dto.setDbType("MYSQL");
		dto.setHost("localhost");
		dto.setName("测试数据库");
		dto.setPassword("fawefaweg");
		dto.setPort(6874);
		dto.setServicename("jksd");
		dto.setSid("335");
		dto.setUsername("sidney");
		DBConnectionService.add(dto);
	}
}
