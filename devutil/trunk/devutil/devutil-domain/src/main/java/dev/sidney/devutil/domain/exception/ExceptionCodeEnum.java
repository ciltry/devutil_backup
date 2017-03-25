/**
 * 
 */
package dev.sidney.devutil.domain.exception;

/**
 * 
 * @author 杨丰光 2015年8月28日16:01:08
 *
 */
public enum ExceptionCodeEnum {


	FOLDER_FOLDER_NAME_INVALID("FOLDER_FOLDER_NAME_INVALID", "文件夹名不合法"), 
	FOLDER_ROOT_CANNOT_BE_REMOVED("FOLDER_ROOT_CANNOT_BE_REMOVED", "根文件夹不可删除"), 
	FOLDER_NOT_EXISTS("FOLDER_NOT_EXISTS", "文件夹不存在"), 
	FOLDER_ALREADY_EXISTS("FOLDER_ALREADY_EXISTS", "文件夹已存在"), 
	FOLDER_PARENT_NOT_EXISTS("FOLDER_PARENT_NOT_EXISTS", "父文件夹不存在"), 
	
	
	
	CONNECTION_TEST_FAILED("CONNECTION_TEST_FAILED", "连接测试失败"), 
	USERNAME_ALREADY_EXISTS("USERNAME_ALREADY_EXISTS", "用户名已被占用"),
	FAILED_TO_ACCESS_DATA("FAILED_TO_ACCESS_DATA", "访问数据失败"),
	FAILED_TO_CONNECT_DB("FAILED_TO_CONNECT_DB", "连接数据库失败");
	
	private String code;
	private String desc;
	
	private ExceptionCodeEnum(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
}
