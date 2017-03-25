/**
 * 
 */
package dev.sidney.devutil.store.model.system;

import dev.sidney.devutil.store.annotation.Field;
import dev.sidney.devutil.store.annotation.Model;
import dev.sidney.devutil.store.model.BaseModel;

/**
 * @author 杨丰光 2015年8月26日18:32:44
 *
 */
@Model(tableName="USER", comment="用户", unique1="username")
public class User extends BaseModel {
	
	/**
	 * uid
	 */
	private static final long serialVersionUID = 1L;
	
	@Field(comment="用户名", size=40, nullable=false)
	private String username;
	
	@Field(comment="密码", size=40, nullable=false)
	private String password;
	
	@Field(comment="昵称", size=256, nullable=false)
	private String nickname;
	
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
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((nickname == null) ? 0 : nickname.hashCode());
		result = prime * result
				+ ((password == null) ? 0 : password.hashCode());
		result = prime * result
				+ ((username == null) ? 0 : username.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (nickname == null) {
			if (other.nickname != null)
				return false;
		} else if (!nickname.equals(other.nickname))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	
}
