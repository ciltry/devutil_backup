/**
 * 
 */
package dev.sidney.devutil.store.enums;

/**
 * @author 杨丰光 2015年8月20日16:20:48
 *
 */
public enum FieldType {

	TIMESTAMP("TIMESTAMP"),
	DATE("DATE"),
	DOUBLE("DOUBLE"),
	INTEGER("INTEGER"),
	LONG("LONG"),
	CHAR("CHAR"),
	VARCHAR2("VARCHAR2"),
	VARCHAR("VARCHAR");
	private String code;
	
	private FieldType(String code) {
		this.code = code;
	}
	
	
	
	
	public String getCode() {
		return code;
	}




	public static FieldType getByCode(String code) {
		for (FieldType e: FieldType.values()) {
			if (e.getCode().equalsIgnoreCase(code)) {
				return e;
			}
		}
		return null;
	}
}
