/**
 * 
 */
package dev.sidney.crawler.simplecrawler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import dev.sidney.crawler.simplecrawler.domain.TaskItemDomain;
import dev.sidney.crawler.simplecrawler.dto.TaskItemDTO;
import dev.sidney.crawler.simplecrawler.model.TaskItem;
import dev.sidney.devutil.store.dao.CommonDAO;

/**
 * @author 杨丰光 2017年3月23日15:00:46
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/context/*.xml" })
public class ModelTest {

	@Resource(name="simplecrawlerDao")
	private CommonDAO dao;
	
	@Resource
	private TaskItemDomain taskItemDomain;
	
	@Resource(name="kuaidaili")
	private ICrawlerTask task;
	
	
	@Test
	public void test1() {
		
//		dao.dropStore(TaskItem.class);
		dao.init();
		
		
		TaskItem i = new TaskItem();
		i.setParentTaskItem("243626");
		i.setStatus("A");
		i.setTaskId("623424");
		i.setUrl("http://www.kuaidaili.com/free/outha/1/");
		dao.insert(i);
		
		TaskItem a = new TaskItem();
		a.setId(i.getId());
		a = dao.queryForObject(a);
		System.out.println(a);
	}
	@Test
	public void test2() {
		TaskItemDTO i = new TaskItemDTO();
		i.setParentTaskItem("243626");
		i.setStatus("A");
		i.setTaskId("623424");
		i.setUrl("http://www.kuaidaili.com/free/outha/1/");
		i.setExceptionTrace("flawkjelf");
		
		taskItemDomain.insert(i);
		
		TaskItemDTO dto = taskItemDomain.queryById(i.getId());
		
		System.out.println(dto);
	}
	
	@Test
	public void test3() {
		TaskItemDTO query = new TaskItemDTO();
		query.setExceptionTrace("flawkjelf");
		TaskItemDTO res = taskItemDomain.peek(query);
		System.out.println(res);
	}
	
	
	@Test
	public void test5() throws IOException {
		URL u = new URL("http://poss-test.masapay.com/poss-web/login.jsp?sign=You%20are%20not%20login");
		HttpURLConnection conn = (HttpURLConnection) u.openConnection();
		conn.getInputStream();
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
		StringBuilder res = new StringBuilder();
		while (true) {
			String str = reader.readLine();
			if (str == null) {
				break;
			}
			res.append(str);
		}
		reader.close();
		System.out.println(res.toString());
	}
	
	
	@Test
	public void test4() {
//		ICrawlerTask task = new CrawlerKuaiDaiLi();
		task.setMaxThreads(1);
		task.setStartUrl("http://www.kuaidaili.com/");
//		task.setStartUrl("http://www.baidu.com/");
//		task.setStartUrl("http://ifeve.com/overview/");
//		task.setStartUrl("http://poss-test.masapay.com/poss-web/login.jsp?sign=You%20are%20not%20login");
		task.setTaskName("快代理");
		task.start();
		
		try {
			Thread.sleep(50000000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
