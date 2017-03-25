/**
 * 
 */
package dev.sidney.devutil.domain.dto;

import dev.sidney.devutil.domain.enums.FolderTypeEnum;


/**
 * @author 杨丰光 2015年10月1日18:00:38
 *
 */
public class FolderDTO extends BaseDTO {

	
	/**
	 * UID
	 */
	private static final long serialVersionUID = -6515114243202796131L;

	private FolderTypeEnum folderType;
	
	private String userId;
	
	private String folderName;
	
	private String parentId;
	
	private String sortId;

	
	
	public FolderTypeEnum getFolderType() {
		return folderType;
	}

	public void setFolderType(FolderTypeEnum folderType) {
		this.folderType = folderType;
	}

	public String getUserId() {
		return userId;
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

	public void setSortId(String sortId) {
		this.sortId = sortId;
	}
	
	
}
