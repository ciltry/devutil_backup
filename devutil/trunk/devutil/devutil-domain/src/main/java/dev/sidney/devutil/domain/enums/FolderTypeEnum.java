/**
 * 
 */
package dev.sidney.devutil.domain.enums;


/**
 * @author 杨丰光 
 *
 */
public enum FolderTypeEnum {

	DB("DB"),
	SSH("SSH");
	
	private String code;
	
	private FolderTypeEnum(String code) {
		this.code = code;
	}
	
	
	
	public String getCode() {
		return code;
	}



	public static FolderTypeEnum getByCode(String code) {
		for (FolderTypeEnum e: FolderTypeEnum.values()) {
			if (e.getCode().equalsIgnoreCase(code)) {
				return e;
			}
		}
		return null;
	}
}
