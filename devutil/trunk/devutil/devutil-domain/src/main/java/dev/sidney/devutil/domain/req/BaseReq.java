/**
 * 
 */
package dev.sidney.devutil.domain.req;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author 杨丰光 2015年8月28日15:58:20
 *
 */
public class BaseReq implements Serializable {

	/**
	 * uid
	 */
	private static final long serialVersionUID = 6955751159139826086L;
	
	private Date reqTime;
	
	public BaseReq() {
		reqTime = new Date();
	}

	public Date getReqTime() {
		return reqTime;
	}

	
}
