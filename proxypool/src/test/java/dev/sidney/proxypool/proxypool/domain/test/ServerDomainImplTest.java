/**
 * 
 */
package dev.sidney.proxypool.proxypool.domain.test;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import dev.sidney.devutil.store.dao.CommonDAO;
import dev.sidney.proxypool.domain.ServerDomain;
import dev.sidney.proxypool.dto.ServerDTO;
import dev.sidney.proxypool.model.Server;

/**
 * @author 杨丰光 2017年3月23日10:12:19
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/context/*.xml" })
public class ServerDomainImplTest {

	@Resource
	private ServerDomain serverDomain;
	
	@Resource(name="proxyDao")
	private CommonDAO dao;
	
	@Test
	public void test1() {
		dao.dropStore(Server.class);
		dao.init();
		
		ServerDTO dto = new ServerDTO();
		dto.setAlias("abc");
		dto.setGmtCreate(new Date());
		dto.setGmtLastTest(new Date());
		dto.setGmtModified(new Date());
		dto.setIp("234236263");
		dto.setMemo("fwaefwe");
		dto.setLocation("罗马尼亚");
		dto.setPort(8080);
		dto.setType("高度匿名");
		dto.setSpeed(300);
		serverDomain.insert(dto);
		
		ServerDTO d = serverDomain.queryById(dto.getId());
		
		System.out.println(d);
	}
}
