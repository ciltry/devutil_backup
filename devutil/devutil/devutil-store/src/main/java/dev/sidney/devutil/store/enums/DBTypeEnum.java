/**
 * 
 */
package dev.sidney.devutil.store.enums;

/**
 * 数据库类型
 * @author 杨丰光 2015年8月20日16:38:11
 *
 */
public enum DBTypeEnum {

	ORACLE("ORACLE"),
	MYSQL("MYSQL"),
	DERBY("DERBY");
	
	private String code;
	
	private DBTypeEnum(String code) {
		this.code = code;
	}
	
	
	
	public String getCode() {
		return code;
	}



	public static DBTypeEnum getByCode(String code) {
		for (DBTypeEnum e: DBTypeEnum.values()) {
			if (e.getCode().equalsIgnoreCase(code)) {
				return e;
			}
		}
		return null;
	}
}
