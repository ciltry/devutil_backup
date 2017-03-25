/**
 * 
 */
package dev.sidney.devutil.domain.req;

/**
 * 
 * @author 杨丰光 2015年8月28日15:57:08
 *
 */
public class RegisterUserReq extends BaseReq {
	/**
	 * uid
	 */
	private static final long serialVersionUID = -555407569771442960L;
	
	private String username;
	private String password;
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
	
	
}
