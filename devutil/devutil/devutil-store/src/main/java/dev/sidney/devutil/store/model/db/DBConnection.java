/**
 * 
 */
package dev.sidney.devutil.store.model.db;

import dev.sidney.devutil.store.annotation.Field;
import dev.sidney.devutil.store.annotation.Model;
import dev.sidney.devutil.store.enums.FieldType;
import dev.sidney.devutil.store.model.BaseModel;

/**
 * @author 杨丰光 2015年8月20日16:14:37
 *
 */
@Model(tableName="DB_CONNECTION", comment="数据库连接", unique1={"userid", "name"})
public class DBConnection extends BaseModel {

	/**
	 * uid
	 */
	private static final long serialVersionUID = -1101646004640188308L;
	
	@Field(comment="库名", size=100)
	private String name;
	
	@Field(type=FieldType.CHAR, comment="系统用户id", size=36)
	private String userid;

	@Field(comment="数据库类型", type=FieldType.CHAR, size=20)
	private String dbType;
	
	@Field(comment="主机名", size=256)
	private String host;
	
	@Field(type=FieldType.INTEGER)
	private Integer port;
	
	@Field(comment="SID", size=30)
	private String sid;
	
	@Field(comment="服务名", size=30)
	private String servicename;
	
	@Field(comment="数据库名", size=30)
	private String dbname;
	
		
	@Field(comment="用户名", size=256)
	private String username;
	
	@Field(comment="密码", size=256)
	private String password;
	
	@Field(comment="编码方式", type=FieldType.CHAR, size=10)
	private String charset;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDbType() {
		return dbType;
	}

	public void setDbType(String dbType) {
		this.dbType = dbType;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public String getServicename() {
		return servicename;
	}

	public void setServicename(String servicename) {
		this.servicename = servicename;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getDbname() {
		return dbname;
	}

	public void setDbname(String dbname) {
		this.dbname = dbname;
	}


	
}
