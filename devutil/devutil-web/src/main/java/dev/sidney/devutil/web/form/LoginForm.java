/**
 * 
 */
package dev.sidney.devutil.web.form;

/**
 * @author 杨丰光 2015年9月18日11:58:07
 *
 */
public class LoginForm extends BaseForm {

	/**
	 * uid
	 */
	private static final long serialVersionUID = 3430986135243943855L;
	private String username;
	private String password;
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
}
