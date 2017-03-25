/**
 * 
 */
package dev.sidney.devutil.store.dto;

import java.util.Date;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import dev.sidney.devutil.store.model.BaseModel;

/**
 * @author 杨丰光 2017年3月23日15:24:54
 *
 */
public abstract class BaseDTO<T extends BaseModel> {

	private String id;
	private Date gmtCreate;
	private Date gmtModified;
	
	
	public BaseDTO() {
	}
	public BaseDTO(T model) {
		constructDTO(model);
	}
	
	
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
	
	public abstract T toModel();
	public abstract void constructDTO(T model);
	
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
