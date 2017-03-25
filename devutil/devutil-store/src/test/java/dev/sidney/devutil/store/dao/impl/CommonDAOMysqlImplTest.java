/**
 * 
 */
package dev.sidney.devutil.store.dao.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import dev.sidney.devutil.store.dao.CommonDAO;
import dev.sidney.devutil.store.model.system.User;

/**
 * @author 杨丰光 2015年8月27日17:06:56
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/context/*.xml" })
public class CommonDAOMysqlImplTest {

	@Resource(name="testDaoMysql")
	private CommonDAO dao;
	
	
	@Test
	public void testInit() {
		dao.init();
	}
	
	
	@Test
	public void testInsert() {
		User user = new User();
		user.setUsername("杨丰光 ");
		user.setPassword("fwalejh");
		user.setNickname("了个违规");
		user.setGmtCreate(new Timestamp(new Date().getTime()));
		user.setGmtModified(new Timestamp(new Date().getTime()));
		
		dao.insert(user);
	}
	
	@Test
	public void testQueryForObject() {
		User user = new User();
		user.setUsername("test " + new Random().nextLong());
		user.setPassword("fwalejh");
		user.setNickname("了个违规");
		user.setGmtCreate(new Date());
		user.setGmtModified(new Date());
		
		dao.insert(user);
		
		User query = new User();
		query.setUsername(user.getUsername());
		query.setNickname("了个违规");
		
		User dbValue = dao.queryForObject(query);

		System.out.println(user.getGmtCreate().getTime());
		System.out.println(dbValue.getGmtCreate().getTime());
		System.out.println(user.getGmtCreate().getClass().getName());
		System.out.println(dbValue.getGmtCreate().getClass().getName());
		Assert.assertEquals(user, dbValue);
	}
	
	@Test
	public void testQueryForList() {
		User query = new User();
		query.setNickname("了个违规");
		
		List<User> list = dao.queryForList(query);

		System.out.println(list);
	}
	
	@Test
	public void testDelete() {
		String key = 10000 + new Random().nextInt(10000) + "" + (10000 + new Random().nextInt(10000));
		for (int i = 0; i < 10; i++) {
			User user = new User();
			user.setUsername("test " + new Random().nextLong());
			user.setPassword("fwalejh");
			user.setNickname(new Random().nextInt() + key + new Random().nextInt());
			user.setGmtCreate(new Date());
			user.setGmtModified(new Date());
			dao.insert(user);
		}
		int updated = dao.delete(new User(), " nickname like ?", String.format("%%%s%%", key));
		System.out.println(updated);
		Assert.assertEquals(10, updated);
	}
	
	@Test
	public void testDelete1() {
			User user = new User();
			user.setUsername("test " + new Random().nextLong());
			user.setPassword("fwalejh");
			user.setNickname(new Random().nextInt() + "访问浪费加我" + new Random().nextInt());
			user.setGmtCreate(new Date());
			user.setGmtModified(new Date());
			dao.insert(user);
		int updated = dao.delete(user);
		System.out.println(updated);
		Assert.assertEquals(1, updated);
	}
	
	@Test
	public void testBatchDeleteById() {
		List<String> idList = new ArrayList<String>();
		int size = 30;
		for (int i = 0; i < size; i++) {
			User user = new User();
			user.setUsername("test " + new Random().nextLong());
			user.setPassword("fwalejh");
			user.setNickname(new Random().nextInt() + "访问浪费加我" + new Random().nextInt());
			user.setGmtCreate(new Date());
			user.setGmtModified(new Date());
			String id = dao.insert(user);
			idList.add(id);
		}
		int updated = dao.batchDeleteById(User.class, idList);
		System.out.println(updated);
		Assert.assertEquals(size, updated);
	}
}
 