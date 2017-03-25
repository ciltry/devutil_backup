/**
 * 
 */
package dev.sidney.proxypool.dto;

import java.util.Date;

/**
 * @author Sidney
 *
 */
public class BaseDTO {

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
