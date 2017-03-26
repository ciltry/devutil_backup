/**
 * 
 */
package dev.sidney.proxypool.model;

import java.lang.reflect.Modifier;
import java.util.Date;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import dev.sidney.devutil.store.annotation.Field;
import dev.sidney.devutil.store.annotation.FieldMapping;
import dev.sidney.devutil.store.annotation.Model;
import dev.sidney.devutil.store.enums.FieldType;
import dev.sidney.devutil.store.model.BaseModel;

/**
 * @author 杨丰光  2017年3月22日17:04:43
 *
 */
@Model(tableName="SERVER")
public class Server extends BaseModel{

	@FieldMapping("ip")
	public static final String COLUMN_IP = "IP";
	@FieldMapping("port")
	public static final String COLUMN_PORT = "PORT";
	@FieldMapping("type")
	public static final String COLUMN_TYPE = "TYPE";
	@FieldMapping("location")
	public static final String COLUMN_LOCATION = "LOCATION";
	@FieldMapping("speed")
	public static final String COLUMN_SPEED = "SPEED";
	@FieldMapping("gmtLastTest")
	public static final String COLUMN_GMT_LAST_TEST = "GMT_LAST_TEST";
	@FieldMapping("alias")
	public static final String COLUMN_ALIAS = "ALIAS";
	@FieldMapping("memo")
	public static final String COLUMN_MEMO = "MEMO";
	@FieldMapping("status")
	public static final String COLUMN_STATUS = "STATUS";
	
	/**
	 * UID
	 */
	private static final long serialVersionUID = 2001486549304181267L;
	@Field(comment="ip", columnName=COLUMN_IP)
	private String ip;
	
	@Field(comment="端口", type=FieldType.INTEGER, columnName=COLUMN_PORT)
	private Integer port;
	
	@Field(comment="匿名度", columnName=COLUMN_TYPE)
	private String type;
	
	@Field(comment="位置", columnName=COLUMN_LOCATION)
	private String location;
	
	@Field(comment="响应速度", type=FieldType.INTEGER, columnName=COLUMN_SPEED)
	private Integer speed;
	
	@Field(comment="上次测试时间", type=FieldType.TIMESTAMP, columnName=COLUMN_GMT_LAST_TEST)
	private Date gmtLastTest;
	@Field(comment="别名", columnName=COLUMN_ALIAS)
	private String alias;
	@Field(comment="备注", columnName=COLUMN_MEMO)
	private String memo;
	@Field(comment="状态 1 可用 0 不可用", type=FieldType.CHAR, size=1, columnName=COLUMN_STATUS)
	private String status;
	
	
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
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
	
	public static void main(String[] args) {
		java.lang.reflect.Field[] fields = Server.class.getDeclaredFields();
		System.out.println(Modifier.PUBLIC);
		System.out.println(Modifier.STATIC);
		System.out.println(Modifier.FINAL);
		for (java.lang.reflect.Field field: fields) {
			System.out.println(field.getName() + " " + field.getModifiers());
		}
		
	}
}
