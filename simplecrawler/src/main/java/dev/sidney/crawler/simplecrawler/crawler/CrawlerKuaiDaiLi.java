/**
 * 
 */
package dev.sidney.crawler.simplecrawler.crawler;

import org.apache.http.client.methods.HttpGet;
import org.springframework.stereotype.Service;

import dev.sidney.crawler.simplecrawler.AbstractCrawlerTask;
import dev.sidney.crawler.simplecrawler.dto.TaskItemDTO;

/**
 * @author Sidney
 *
 */
@Service("kuaidaili")
public class CrawlerKuaiDaiLi extends AbstractCrawlerTask {

	@Override
	public void initHttpGet(HttpGet httpGet) {
		httpGet.setHeader("Cookie", "Cookie: channelid=0; sid=1490336592440805; _ga=GA1.2.208353969.1490336819; Hm_lvt_7ed65b1cc4b810e9fd37959c9bb51b31=1490248369,1490327374,1490336051,1490336240; Hm_lpvt_7ed65b1cc4b810e9fd37959c9bb51b31=1490338168; _ydclearance=f45c4a86ae2410f3a981b05b-58b5-4f67-8a18-dfc0b33f4b12-1490351689");
	}

	@Override
	public boolean skipUrl(String url) {
		return !url.contains("/proxylist/") && !url.contains("/free/");
	}

	@Override
	protected void processPage(TaskItemDTO taskItem, String pageContent) {
		
	}

}
