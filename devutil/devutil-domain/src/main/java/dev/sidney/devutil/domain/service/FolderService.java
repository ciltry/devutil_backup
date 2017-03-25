/**
 * 
 */
package dev.sidney.devutil.domain.service;

import java.util.List;

import dev.sidney.devutil.domain.dto.FolderDTO;
import dev.sidney.devutil.domain.enums.FolderTypeEnum;
import dev.sidney.devutil.domain.exception.BusinessException;

/**
 * @author 杨丰光 2015年10月1日18:23:56
 *
 */
public interface FolderService {

	List<FolderDTO> getListByUserId(String userId, FolderTypeEnum folderType) throws BusinessException;
	
	void add(FolderDTO dto) throws BusinessException;
	/**
	 * 删除目录
	 * @param dto
	 * @return 删除的目录数
	 * @throws BusinessException
	 */
	int remove(FolderDTO dto) throws BusinessException;
	void rename(FolderDTO dto) throws BusinessException;
}
