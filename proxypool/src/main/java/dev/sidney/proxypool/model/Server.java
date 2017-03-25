/**
 * 
 */
package dev.sidney.proxypool.model;

import java.util.Date;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import dev.sidney.devutil.store.annotation.Field;
import dev.sidney.devutil.store.annotation.Model;
import dev.sidney.devutil.store.enums.FieldType;
import dev.sidney.devutil.store.model.BaseModel;

/**
 * @author 杨丰光  2017年3月22日17:04:43
 *
 */
@Model(tableName="SERVER")
public class Server extends BaseModel{

	/**
	 * UID
	 */
	private static final long serialVersionUID = 2001486549304181267L;
	@Field(comment="ip")
	private String ip;
	
	@Field(comment="端口", type=FieldType.INTEGER)
	private Integer port;
	
	@Field(comment="匿名度")
	private String type;
	
	@Field(comment="位置")
	private String location;
	
	@Field(comment="响应速度", type=FieldType.INTEGER)
	private Integer speed;
	
	@Field(comment="上次测试时间", type=FieldType.TIMESTAMP)
	private Date gmtLastTest;
	@Field(comment="别名")
	private String alias;
	@Field(comment="备注")
	private String memo;
	
	
	public Integer getPort() {
		return port;
	}
	public void setPort(Integer port) {
		this.port = port;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public Integer getSpeed() {
		return speed;
	}
	public void setSpeed(Integer speed) {
		this.speed = speed;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public Date getGmtLastTest() {
		return gmtLastTest;
	}
	public void setGmtLastTest(Date gmtLastTest) {
		this.gmtLastTest = gmtLastTest;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
