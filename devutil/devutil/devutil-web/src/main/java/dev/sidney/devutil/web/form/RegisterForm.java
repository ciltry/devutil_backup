/**
 * 
 */
package dev.sidney.devutil.web.form;

/**
 * 注册表单
 * @author 杨丰光 2015年8月27日17:51:39
 *
 */
public class RegisterForm  extends BaseForm {

	/**
	 * uid
	 */
	private static final long serialVersionUID = 2935248164761297963L;
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
