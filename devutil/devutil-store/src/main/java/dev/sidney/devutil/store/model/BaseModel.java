/**
 * 
 */
package dev.sidney.devutil.store.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import dev.sidney.devutil.store.annotation.Field;
import dev.sidney.devutil.store.annotation.FieldMapping;
import dev.sidney.devutil.store.annotation.Model;
import dev.sidney.devutil.store.enums.FieldType;

/**
 * 
 * @author 杨丰光 2015年8月20日16:14:27
 *
 */
@Model(primaryKey={"id"}, tableName="")
public class BaseModel implements java.io.Serializable {

	@FieldMapping("id")
	public static final String COLUMN_ID = "ID";
	@FieldMapping("gmtCreate")
	public static final String COLUMN_GMT_CREATE = "GMT_CREATE";
	@FieldMapping("gmtModified")
	public static final String COLUMN_GMT_MODIFIED = "GMT_MODIFIED";
	
	/**
	 * uid
	 */
	private static final long serialVersionUID = 196652988431996142L;
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Field(type=FieldType.CHAR, size=36, columnName=COLUMN_ID, nullable=false)
	private String id;

	@Field(comment="创建时间", type=FieldType.TIMESTAMP, columnName=COLUMN_GMT_CREATE, nullable=false)
	private Date gmtCreate;
	@Field(comment="修改时间", type=FieldType.TIMESTAMP, columnName=COLUMN_GMT_MODIFIED, nullable=false)
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



	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

	
	protected boolean dateEquals(Date d1, Date d2) {
		if (d1 == d2) {
			return true;
		}
		if (d1 != null && d2 != null) {
			return sdf.format(d1).equals(sdf.format(d2));
		}
		return false;
	}




	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((gmtCreate == null) ? 0 : gmtCreate.hashCode());
		result = prime * result
				+ ((gmtModified == null) ? 0 : gmtModified.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}




	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		BaseModel other = (BaseModel) obj;
		if (!this.dateEquals(this.gmtCreate, other.gmtCreate)) {
			return false;
		}
		if (!this.dateEquals(this.gmtModified, other.gmtModified)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}
}
