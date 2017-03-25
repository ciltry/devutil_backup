/**
 * 
 */
package dev.sidney.devutil.domain.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 杨丰光 2015年9月18日13:50:18
 *
 */
public class BaseDTO implements Serializable {

	
	/**
	 * uid
	 */
	private static final long serialVersionUID = 1757682768618439440L;
	
	private String id;
	
	private Date gmtCreate;
	
	private Date gmtModified;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public Date getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	
}
