/**
 * 
 */
package dev.sidney.devutil.domain.service.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import dev.sidney.devutil.domain.dto.FolderDTO;
import dev.sidney.devutil.domain.enums.FolderTypeEnum;
import dev.sidney.devutil.domain.exception.BusinessException;
import dev.sidney.devutil.domain.exception.ExceptionCodeEnum;
import dev.sidney.devutil.domain.service.FolderService;
import dev.sidney.devutil.store.dao.CommonDAO;
import dev.sidney.devutil.store.model.db.Folder;

/**
 * @author 杨丰光 2015年10月1日18:29:05
 *
 */
@Service
public class FolderServiceImpl implements FolderService {

	private static final Logger logger = LoggerFactory.getLogger(FolderServiceImpl.class);
	
	@Resource(name="commonDao")
	private CommonDAO dao;
	
	/* (non-Javadoc)
	 * @see dev.sidney.devutil.domain.service.FolderService#getListByUserId(java.lang.String)
	 */
	@Override
	public List<FolderDTO> getListByUserId(String userId, FolderTypeEnum folderType) {
		Folder dto = new Folder();
		dto.setUserId(userId);
		dto.setFolderType(folderType.getCode());
		List<Folder> modelList = dao.queryForList(dto, String.format(" ORDER BY %s", Folder.getSortIdColumnName()));
		List<FolderDTO> list = new ArrayList<FolderDTO>();
		for (Folder model: modelList) {
			list.add(poToBo(model));
		}
		return list;
	}
	
	private FolderDTO poToBo(Folder folder) {
		
		FolderDTO dto = null;
		if (folder != null) {
			dto = new FolderDTO();
			dto.setFolderName(folder.getFolderName());
			dto.setFolderType(FolderTypeEnum.getByCode(folder.getFolderType()));
			dto.setGmtCreate(folder.getGmtCreate());
			dto.setGmtModified(folder.getGmtModified());
			dto.setId(folder.getId());
			dto.setParentId(folder.getParentId());
			dto.setSortId(folder.getSortId());
			dto.setUserId(folder.getUserId());
		}
		
		return dto;
	}
	
	private Folder boToPo(FolderDTO dto) {
		Folder model = null;
		
		if (dto != null) {
			model = new Folder();
			model.setFolderName(dto.getFolderName());
			model.setFolderType(dto.getFolderType().getCode());
			model.setGmtCreate(dto.getGmtCreate());
			model.setGmtModified(dto.getGmtModified());
			model.setId(dto.getId());
			model.setParentId(dto.getParentId());
			model.setSortId(dto.getSortId());
			model.setUserId(dto.getUserId());
		}
		return model;
	}
	

	/* (non-Javadoc)
	 * @see dev.sidney.devutil.domain.service.FolderService#add(dev.sidney.devutil.domain.dto.FolderDTO)
	 */
	@Override
	@Transactional(value="transactionManager", rollbackFor=BusinessException.class)
	public void add(FolderDTO srcDto) throws BusinessException {
		validateFolderName(srcDto.getFolderName());
		FolderDTO dto = new FolderDTO();
		BeanUtils.copyProperties(srcDto, dto);
		dto.setId(null);
		dto.setSortId(null);
		// 如果已经存在，抛出异常
		if (isFolderExists(dto)) {
			throw new BusinessException(ExceptionCodeEnum.FOLDER_ALREADY_EXISTS);
		}
		// 父文件夹如果不存在，抛出异常
		if (!isRootFolder(dto) && !isParentFolderExists(dto)) {
			throw new BusinessException(ExceptionCodeEnum.FOLDER_PARENT_NOT_EXISTS);
		}
		
		String nextSortId = getNextSortId(dto);
		logger.info("下一个sortId: " + nextSortId);
		dto.setSortId(nextSortId);
		dto.setId(dao.insert(boToPo(dto)));
		
		srcDto.setSortId(dto.getSortId());
		srcDto.setId(dto.getId());
	}
	
	private String getParentFolderSortId(FolderDTO folder) {
		FolderDTO parent = this.getParentFolder(folder);
		return parent == null ? null : parent.getSortId();
		
	}
	
	/**
	 * 获取下一个排序ID
	 * @param dto
	 * @return
	 */
	private String getNextSortId(FolderDTO dto) {
		String maxSortId = getMaxSortId(dto);
		String nextSortId = null;
		if (maxSortId == null) {
			String parentSortId = this.getParentFolderSortId(dto);
			nextSortId = parentSortId == null ? "00" : parentSortId + "00";
		} else {
			String prefix = maxSortId.substring(0, maxSortId.length() - 2);
			maxSortId = maxSortId.substring(maxSortId.length() - 2, maxSortId.length());
			nextSortId = Integer.toHexString(Integer.parseInt(maxSortId, 16) + 1).toUpperCase();
			if (nextSortId.length() == 1) {
				nextSortId = "0" + nextSortId;
			}
			nextSortId = prefix + nextSortId;
		}
		return nextSortId;
	}
	
	/**
	 * 查找同级目录最大的排序ID
	 * @param dto
	 * @return
	 */
	private String getMaxSortId(FolderDTO dto) {
		Folder model = new Folder();
		model.setParentId(dto.getParentId());
		model.setUserId(dto.getUserId());
		model.setFolderType(dto.getFolderType().getCode());
		RowMapper<String> rowMapper = new RowMapper<String>(){

			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString(1);
			}};
		String maxSortId = dao.queryCustom(model, rowMapper, Arrays.asList(new String[]{String.format("MAX(%s)", Folder.getSortIdColumnName())}), null);
		return maxSortId;
	}
	
	private boolean isRootFolder(FolderDTO dto) {
		return "0".equals(dto.getParentId());
	}

	private boolean isFolderExists(FolderDTO dto) {
		return queryFolder(dto) != null;
	}
	
	private FolderDTO queryFolder(FolderDTO dto) {
		Folder folder = new Folder();
		folder.setUserId(dto.getUserId());
		folder.setFolderName(dto.getFolderName());
		folder.setParentId(dto.getParentId());
		folder.setFolderType(dto.getFolderType().getCode());
		folder.setId(dto.getId());
		return this.poToBo(dao.queryForObject(folder));
	}
	
	private boolean isParentFolderExists(FolderDTO dto) {
		return this.getParentFolder(dto) != null;
	}
	
	private FolderDTO getParentFolder(FolderDTO dto) {
		Folder folder = new Folder();
		folder.setUserId(dto.getUserId());
		folder.setFolderType(dto.getFolderType().getCode());
		folder.setId(dto.getParentId());
		return this.poToBo(dao.queryForObject(folder));
	}
	

	/* (non-Javadoc)
	 * @see dev.sidney.devutil.domain.service.FolderService#remove(dev.sidney.devutil.domain.dto.FolderDTO)
	 */
	@Override
	public int remove(FolderDTO dto) throws BusinessException  {
		if (!isFolderExists(dto)) {
			throw new BusinessException(ExceptionCodeEnum.FOLDER_NOT_EXISTS);
		}
		if (this.isRootFolder(dto)) {
			throw new BusinessException(ExceptionCodeEnum.FOLDER_ROOT_CANNOT_BE_REMOVED);
		}
		Folder model = new Folder();
		model.setUserId(dto.getUserId());
		model.setFolderType(dto.getFolderType().getCode());
		
		return dao.delete(model, String.format("%s LIKE ?", Folder.getSortIdColumnName()), String.format("%s%%", dto.getSortId()));
	}

	
	private void validateFolderName(String name) throws BusinessException {
		if (StringUtils.isEmpty(name) || StringUtils.isEmpty(StringUtils.trimWhitespace(name))) {
			throw new BusinessException(ExceptionCodeEnum.FOLDER_FOLDER_NAME_INVALID);
		}
	}
	
	/* (non-Javadoc)
	 * @see dev.sidney.devutil.domain.service.FolderService#rename(dev.sidney.devutil.domain.dto.FolderDTO)
	 */
	@Override
	public void rename(FolderDTO dto)  throws BusinessException {
		validateFolderName(dto.getFolderName());
		FolderDTO clone = new FolderDTO();
		BeanUtils.copyProperties(dto, clone);
		clone.setFolderName(null);
		if (!isFolderExists(clone)) {
			throw new BusinessException(ExceptionCodeEnum.FOLDER_NOT_EXISTS);
		}
		Folder model = new Folder();
		model.setId(dto.getId());
		model.setFolderName(dto.getFolderName());
		int updated = dao.update(model, String.format("%s=? AND %s = ?", Folder.getUserIdColumnName(), Folder.getFolderTypeColumnName()), dto.getUserId(), dto.getFolderType().getCode());
		System.out.println(updated);
	}

}
