/**
 * 
 */
package dev.sidney.crawler.simplecrawler.datacapture;

import java.util.List;
import java.util.regex.Pattern;

import dev.sidney.proxypool.dto.ServerDTO;

/**
 * @author Sidney
 *
 */
public class KuaiDaiLiDataCapture extends DataCapture<ServerDTO> {
	
	@Override
	public List<ServerDTO> buildData(String captureKey, String[] dataList) {
		List<ServerDTO> list = null;
		if ("表格".equals(captureKey)) {
			
		}
		return list;
	}

	@Override
	public List<ServerDTO> captureData(String input) {
		Pattern pt = Pattern.compile("<div\\s+id=\"list\"[\\w\\W]*</div>");
		return this.captureData("表格", pt, input);
	}

}
