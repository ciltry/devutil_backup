/**
 * 
 */
package dev.sidney.crawler.simplecrawler.datacapture;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Sidney
 *
 */
public abstract class DataCapture<T>{

	public abstract List<T> captureData(String input);
	
	protected final List<T> captureData(String captureKey, Pattern pt, String input) {
		Matcher matcher = pt.matcher(input);
		List<T> list = new ArrayList<T>();
		while (matcher.find()) {
			int groupCount = matcher.groupCount();
			String[] dataArray = new String[groupCount];
			for (int i = 0; i < groupCount + 1; i++) {
				dataArray[i] = matcher.group(i);
			}
			list.addAll(this.buildData(captureKey, dataArray));
		}
		return null;
	}
	
	public abstract List<T> buildData(String captureKey, String[] dataList);
}
