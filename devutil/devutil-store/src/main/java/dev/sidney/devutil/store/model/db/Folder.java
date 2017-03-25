/**
 * 
 */
package dev.sidney.devutil.store.model.db;

import dev.sidney.devutil.store.annotation.Field;
import dev.sidney.devutil.store.annotation.Model;
import dev.sidney.devutil.store.enums.FieldType;
import dev.sidney.devutil.store.model.BaseModel;

/**
 * @author 杨丰光 2015年10月1日18:00:38
 *
 */
@Model(tableName="FOLDER", comment="文件夹", unique1={"userId", "sortId", "parentId", "folderName"})
public class Folder extends BaseModel {

	/**
	 * UID
	 */
	private static final long serialVersionUID = -824906891976183184L;
	
	@Field(type=FieldType.CHAR, comment="文件夹类型", size=10, nullable=false)
	private String folderType;
	
	@Field(type=FieldType.CHAR, comment="系统用户id", size=36 , nullable=false)
	private String userId;
	
	@Field(comment="文件夹名", size=30, nullable=false)
	private String folderName;
	
	@Field(type=FieldType.CHAR, comment="父目录id", size=36, nullable=false)
	private String parentId;
	
	@Field(comment="排序id", size=32, nullable=false)
	private String sortId;

	
	
	
	public String getFolderType() {
		return folderType;
	}
	
	public static String getFolderTypeColumnName() {
		return "folderType";
	}

	public void setFolderType(String folderType) {
		this.folderType = folderType;
	}

	public String getUserId() {
		return userId;
	}
	
	public static String getUserIdColumnName() {
		return "userId";
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getFolderName() {
		return folderName;
	}

	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getSortId() {
		return sortId;
	}
	
	public static String getSortIdColumnName() {
		return "sortId";
	}

	public void setSortId(String sortId) {
		this.sortId = sortId;
	}
	
	
}
