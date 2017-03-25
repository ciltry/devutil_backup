/**
 * 
 */
package dev.sidney.devutil.domain.dto;


/**
 * @author 杨丰光 2015年8月20日16:14:37
 *
 */
public class DBConnectionDTO extends BaseDTO  {

	
	/**
	 * uid
	 */
	private static final long serialVersionUID = 5291658822158179502L;

	private String name;
	
	private String userid;

	private String dbType;
	
	private String host;
	
	private Integer port;
	
	private String sid;
	
	private String username;
	
	private String servicename;
	
	private String dbname;
	
	private String password;
	
	private String charset;



	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

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

	public String getDbname() {
		return dbname;
	}

	public void setDbname(String dbname) {
		this.dbname = dbname;
	}

}
