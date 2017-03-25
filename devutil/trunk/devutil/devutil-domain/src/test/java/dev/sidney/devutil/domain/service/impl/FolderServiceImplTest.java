/**
 * 
 */
package dev.sidney.devutil.domain.service.impl;

import java.util.Date;
import java.util.Random;
import java.util.UUID;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import dev.sidney.devutil.domain.dto.FolderDTO;
import dev.sidney.devutil.domain.enums.FolderTypeEnum;
import dev.sidney.devutil.domain.exception.BusinessException;
import dev.sidney.devutil.domain.exception.ExceptionCodeEnum;
import dev.sidney.devutil.domain.service.FolderService;

/**
 * @author 杨丰光 2015年10月1日21:40:07
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/context/*.xml" })
public class FolderServiceImplTest {

	@Resource
	private FolderService folderService;
	
	
	/**
	 * 
	 * @throws BusinessException
	 */
	@Test
	public void 正常系_创建文件夹时无根目录() throws BusinessException {
		FolderDTO f = new FolderDTO();
		f.setFolderName("test folder" + new Random().nextInt());
		f.setFolderType(FolderTypeEnum.DB);
		f.setParentId("0");
		f.setUserId(UUID.randomUUID().toString());
		f.setGmtCreate(new Date());
		f.setGmtModified(f.getGmtCreate());
		folderService.add(f);
	}
	
	@Test
	public void 正常系_创建文件夹_创建二级目录() throws BusinessException {
		String userId = UUID.randomUUID().toString();
		
		FolderDTO f = new FolderDTO();
		f.setFolderName("test folder" + new Random().nextInt());
		f.setFolderType(FolderTypeEnum.DB);
		f.setParentId("0");
		f.setUserId(userId);
		f.setGmtCreate(new Date());
		f.setGmtModified(f.getGmtCreate());
		folderService.add(f);
		
		FolderDTO subFolder = new FolderDTO();
		subFolder.setFolderName("test folder" + new Random().nextInt());
		subFolder.setFolderType(FolderTypeEnum.DB);
		subFolder.setParentId(f.getId());
		subFolder.setUserId(userId);
		subFolder.setGmtCreate(new Date());
		subFolder.setGmtModified(f.getGmtCreate());
		folderService.add(subFolder);
		
		Assert.assertEquals(4, subFolder.getSortId().length());
	}
	
	@Test
	public void 正常系_创建文件夹_确认序号() throws BusinessException {
		String userId = UUID.randomUUID().toString();
		
		FolderDTO f = new FolderDTO();
		f.setFolderName("test folder" + new Random().nextInt());
		f.setFolderType(FolderTypeEnum.DB);
		f.setParentId("0");
		f.setUserId(userId);
		f.setGmtCreate(new Date());
		f.setGmtModified(f.getGmtCreate());
		folderService.add(f);
		
		FolderDTO subFolder = new FolderDTO();
		subFolder.setFolderName("test folder" + new Random().nextInt());
		subFolder.setFolderType(FolderTypeEnum.DB);
		subFolder.setParentId(f.getId());
		subFolder.setUserId(userId);
		subFolder.setGmtCreate(new Date());
		subFolder.setGmtModified(f.getGmtCreate());
		folderService.add(subFolder);
		
		Assert.assertEquals("0000", subFolder.getSortId());
		
		subFolder = new FolderDTO();
		subFolder.setFolderName("test folder" + new Random().nextInt());
		subFolder.setFolderType(FolderTypeEnum.DB);
		subFolder.setParentId(f.getId());
		subFolder.setUserId(userId);
		subFolder.setGmtCreate(new Date());
		subFolder.setGmtModified(f.getGmtCreate());
		folderService.add(subFolder);
		
		Assert.assertEquals("0001", subFolder.getSortId());
		
		FolderDTO subsubFolder = new FolderDTO();
		subsubFolder.setFolderName("test folder" + new Random().nextInt());
		subsubFolder.setFolderType(FolderTypeEnum.DB);
		subsubFolder.setParentId(subFolder.getId());
		subsubFolder.setUserId(userId);
		subsubFolder.setGmtCreate(new Date());
		subsubFolder.setGmtModified(f.getGmtCreate());
		folderService.add(subsubFolder);
		
		Assert.assertEquals("000100", subsubFolder.getSortId());
	}
	
	@Test
	public void 异常系_创建文件夹_文件夹已存在() throws BusinessException {
		String userId = UUID.randomUUID().toString();
		
		FolderDTO f = new FolderDTO();
		f.setFolderName("test folder" + new Random().nextInt());
		f.setFolderType(FolderTypeEnum.DB);
		f.setParentId("0");
		f.setUserId(userId);
		f.setGmtCreate(new Date());
		f.setGmtModified(f.getGmtCreate());
		folderService.add(f);
		String parentId = f.getId();
		try {
			folderService.add(f);
			Assert.fail();
		} catch (BusinessException e) {
			Assert.assertEquals(ExceptionCodeEnum.FOLDER_ALREADY_EXISTS, e.getExceptionCode());
		}
		
		FolderDTO subFolder = new FolderDTO();
		subFolder.setFolderName("test folder" + new Random().nextInt());
		subFolder.setFolderType(FolderTypeEnum.DB);
		subFolder.setParentId(parentId);
		subFolder.setUserId(userId);
		subFolder.setGmtCreate(new Date());
		subFolder.setGmtModified(f.getGmtCreate());
		folderService.add(subFolder);
		try {
			folderService.add(subFolder);
			Assert.fail();
		} catch (BusinessException e) {
			Assert.assertEquals(ExceptionCodeEnum.FOLDER_ALREADY_EXISTS, e.getExceptionCode());
		}
	}
	
	@Test
	public void 异常系_创建文件夹_父目录不存在() throws BusinessException {
		String userId = UUID.randomUUID().toString();
		
		FolderDTO f = new FolderDTO();
		f.setFolderName("test folder" + new Random().nextInt());
		f.setFolderType(FolderTypeEnum.DB);
		f.setParentId(UUID.randomUUID().toString());
		f.setUserId(userId);
		f.setGmtCreate(new Date());
		f.setGmtModified(f.getGmtCreate());
		try {
			folderService.add(f);
			Assert.fail();
		} catch (BusinessException e) {
			Assert.assertEquals(ExceptionCodeEnum.FOLDER_PARENT_NOT_EXISTS, e.getExceptionCode());
		}
	}
	
	@Test
	public void 异常系_创建文件夹_在其他User的目录下创建() throws BusinessException {
		String userId = UUID.randomUUID().toString();
		
		FolderDTO f = new FolderDTO();
		f.setFolderName("test folder" + new Random().nextInt());
		f.setFolderType(FolderTypeEnum.DB);
		f.setParentId("0");
		f.setUserId(userId);
		f.setGmtCreate(new Date());
		f.setGmtModified(f.getGmtCreate());
		folderService.add(f);
		String parentId = f.getId();
		
		FolderDTO subFolder = new FolderDTO();
		subFolder.setFolderName("test folder" + new Random().nextInt());
		subFolder.setFolderType(FolderTypeEnum.DB);
		subFolder.setParentId(parentId);
		subFolder.setUserId(UUID.randomUUID().toString());
		subFolder.setGmtCreate(new Date());
		subFolder.setGmtModified(f.getGmtCreate());
		try {
			folderService.add(subFolder);
			Assert.fail();
		} catch (BusinessException e) {
			Assert.assertEquals(ExceptionCodeEnum.FOLDER_PARENT_NOT_EXISTS, e.getExceptionCode());
		}
		
		subFolder = new FolderDTO();
		subFolder.setFolderName("test folder" + new Random().nextInt());
		subFolder.setFolderType(FolderTypeEnum.DB);
		subFolder.setParentId(parentId);
		subFolder.setUserId(userId);
		subFolder.setGmtCreate(new Date());
		subFolder.setGmtModified(f.getGmtCreate());
		folderService.add(subFolder);
		
		
		FolderDTO subsubFolder = new FolderDTO();
		subsubFolder.setFolderName("test folder" + new Random().nextInt());
		subsubFolder.setFolderType(FolderTypeEnum.DB);
		subsubFolder.setParentId(subFolder.getId());
		subsubFolder.setUserId(UUID.randomUUID().toString());
		subsubFolder.setGmtCreate(new Date());
		subsubFolder.setGmtModified(f.getGmtCreate());
		try {
			folderService.add(subsubFolder);
			Assert.fail();
		} catch (BusinessException e) {
			Assert.assertEquals(ExceptionCodeEnum.FOLDER_PARENT_NOT_EXISTS, e.getExceptionCode());
		}
	}
	
	@Test
	public void 异常系_创建文件夹_不同类型的文件夹隔离() throws BusinessException {
		String userId = UUID.randomUUID().toString();
		
		FolderDTO f = new FolderDTO();
		f.setFolderName("test folder" + new Random().nextInt());
		f.setFolderType(FolderTypeEnum.DB);
		f.setParentId("0");
		f.setUserId(userId);
		f.setGmtCreate(new Date());
		f.setGmtModified(f.getGmtCreate());
		folderService.add(f);
		String parentId = f.getId();
		
		FolderDTO subFolder = new FolderDTO();
		subFolder.setFolderName("test folder" + new Random().nextInt());
		subFolder.setFolderType(FolderTypeEnum.SSH);
		subFolder.setParentId(parentId);
		subFolder.setUserId(userId);
		subFolder.setGmtCreate(new Date());
		subFolder.setGmtModified(f.getGmtCreate());
		try {
			folderService.add(subFolder);
			Assert.fail();
		} catch (BusinessException e) {
			Assert.assertEquals(ExceptionCodeEnum.FOLDER_PARENT_NOT_EXISTS, e.getExceptionCode());
		}
		
		subFolder = new FolderDTO();
		subFolder.setFolderName("test folder" + new Random().nextInt());
		subFolder.setFolderType(FolderTypeEnum.DB);
		subFolder.setParentId(parentId);
		subFolder.setUserId(userId);
		subFolder.setGmtCreate(new Date());
		subFolder.setGmtModified(f.getGmtCreate());
		folderService.add(subFolder);
		
		
		FolderDTO subsubFolder = new FolderDTO();
		subsubFolder.setFolderName("test folder" + new Random().nextInt());
		subsubFolder.setFolderType(FolderTypeEnum.SSH);
		subsubFolder.setParentId(subFolder.getId());
		subsubFolder.setUserId(userId);
		subsubFolder.setGmtCreate(new Date());
		subsubFolder.setGmtModified(f.getGmtCreate());
		try {
			folderService.add(subsubFolder);
			Assert.fail();
		} catch (BusinessException e) {
			Assert.assertEquals(ExceptionCodeEnum.FOLDER_PARENT_NOT_EXISTS, e.getExceptionCode());
		}
	}
	
	
	@Test
	public void 正常系_删除文件夹_删除二级目录() throws BusinessException {
		String userId = UUID.randomUUID().toString();
		
		FolderDTO f = new FolderDTO();
		f.setFolderName("test folder" + new Random().nextInt());
		f.setFolderType(FolderTypeEnum.DB);
		f.setParentId("0");
		f.setUserId(userId);
		f.setGmtCreate(new Date());
		f.setGmtModified(f.getGmtCreate());
		folderService.add(f);
		
		FolderDTO subFolder = new FolderDTO();
		subFolder.setFolderName("test folder" + new Random().nextInt());
		subFolder.setFolderType(FolderTypeEnum.DB);
		subFolder.setParentId(f.getId());
		subFolder.setUserId(userId);
		subFolder.setGmtCreate(new Date());
		subFolder.setGmtModified(f.getGmtCreate());
		folderService.add(subFolder);
		
		subFolder = new FolderDTO();
		subFolder.setFolderName("test folder" + new Random().nextInt());
		subFolder.setFolderType(FolderTypeEnum.DB);
		subFolder.setParentId(f.getId());
		subFolder.setUserId(userId);
		subFolder.setGmtCreate(new Date());
		subFolder.setGmtModified(f.getGmtCreate());
		folderService.add(subFolder);
		
		
		FolderDTO subsubFolder = new FolderDTO();
		subsubFolder.setFolderName("test folder" + new Random().nextInt());
		subsubFolder.setFolderType(FolderTypeEnum.DB);
		subsubFolder.setParentId(subFolder.getId());
		subsubFolder.setUserId(userId);
		subsubFolder.setGmtCreate(new Date());
		subsubFolder.setGmtModified(f.getGmtCreate());
		folderService.add(subsubFolder);
		
		int removed = folderService.remove(subFolder);
		Assert.assertEquals(2, removed);
	}
	
	@Test
	public void 异常系_删除文件夹_目录不存在() throws BusinessException {
		String userId = UUID.randomUUID().toString();
		
		FolderDTO f = new FolderDTO();
		f.setFolderName("test folder" + new Random().nextInt());
		f.setFolderType(FolderTypeEnum.DB);
		f.setParentId("0");
		f.setUserId(userId);
		f.setGmtCreate(new Date());
		f.setGmtModified(f.getGmtCreate());
		folderService.add(f);
		
		FolderDTO subFolder = new FolderDTO();
		subFolder.setFolderName("test folder" + new Random().nextInt());
		subFolder.setFolderType(FolderTypeEnum.DB);
		subFolder.setParentId(f.getId());
		subFolder.setUserId(userId);
		subFolder.setGmtCreate(new Date());
		subFolder.setGmtModified(f.getGmtCreate());
		folderService.add(subFolder);
		
		subFolder = new FolderDTO();
		subFolder.setFolderName("test folder" + new Random().nextInt());
		subFolder.setFolderType(FolderTypeEnum.DB);
		subFolder.setParentId(f.getId());
		subFolder.setUserId(userId);
		subFolder.setGmtCreate(new Date());
		subFolder.setGmtModified(f.getGmtCreate());
		folderService.add(subFolder);
		
		
		FolderDTO subsubFolder = new FolderDTO();
		subsubFolder.setFolderName("test folder" + new Random().nextInt());
		subsubFolder.setFolderType(FolderTypeEnum.DB);
		subsubFolder.setParentId(subFolder.getId());
		subsubFolder.setUserId(userId);
		subsubFolder.setGmtCreate(new Date());
		subsubFolder.setGmtModified(f.getGmtCreate());
		folderService.add(subsubFolder);
		subFolder.setId(UUID.randomUUID().toString());
		try {
			int removed = folderService.remove(subFolder);
			Assert.fail();
		} catch (BusinessException e) {
			Assert.assertEquals(ExceptionCodeEnum.FOLDER_NOT_EXISTS, e.getExceptionCode());
		}
	}
	
	@Test
	public void 异常系_删除文件夹_删除别人的目录() throws BusinessException {
		String userId = UUID.randomUUID().toString();
		
		FolderDTO f = new FolderDTO();
		f.setFolderName("test folder" + new Random().nextInt());
		f.setFolderType(FolderTypeEnum.DB);
		f.setParentId("0");
		f.setUserId(userId);
		f.setGmtCreate(new Date());
		f.setGmtModified(f.getGmtCreate());
		folderService.add(f);
		
		FolderDTO subFolder = new FolderDTO();
		subFolder.setFolderName("test folder" + new Random().nextInt());
		subFolder.setFolderType(FolderTypeEnum.DB);
		subFolder.setParentId(f.getId());
		subFolder.setUserId(userId);
		subFolder.setGmtCreate(new Date());
		subFolder.setGmtModified(f.getGmtCreate());
		folderService.add(subFolder);
		
		subFolder = new FolderDTO();
		subFolder.setFolderName("test folder" + new Random().nextInt());
		subFolder.setFolderType(FolderTypeEnum.DB);
		subFolder.setParentId(f.getId());
		subFolder.setUserId(userId);
		subFolder.setGmtCreate(new Date());
		subFolder.setGmtModified(f.getGmtCreate());
		folderService.add(subFolder);
		
		
		FolderDTO subsubFolder = new FolderDTO();
		subsubFolder.setFolderName("test folder" + new Random().nextInt());
		subsubFolder.setFolderType(FolderTypeEnum.DB);
		subsubFolder.setParentId(subFolder.getId());
		subsubFolder.setUserId(userId);
		subsubFolder.setGmtCreate(new Date());
		subsubFolder.setGmtModified(f.getGmtCreate());
		folderService.add(subsubFolder);
		subFolder.setUserId(UUID.randomUUID().toString());
		try {
			int removed = folderService.remove(subFolder);
			Assert.fail();
		} catch (BusinessException e) {
			Assert.assertEquals(ExceptionCodeEnum.FOLDER_NOT_EXISTS, e.getExceptionCode());
		}
	}
	
	@Test
	public void 异常系_删除文件夹_删除类型不一致() throws BusinessException {
		String userId = UUID.randomUUID().toString();
		
		FolderDTO f = new FolderDTO();
		f.setFolderName("test folder" + new Random().nextInt());
		f.setFolderType(FolderTypeEnum.DB);
		f.setParentId("0");
		f.setUserId(userId);
		f.setGmtCreate(new Date());
		f.setGmtModified(f.getGmtCreate());
		folderService.add(f);
		
		FolderDTO subFolder = new FolderDTO();
		subFolder.setFolderName("test folder" + new Random().nextInt());
		subFolder.setFolderType(FolderTypeEnum.DB);
		subFolder.setParentId(f.getId());
		subFolder.setUserId(userId);
		subFolder.setGmtCreate(new Date());
		subFolder.setGmtModified(f.getGmtCreate());
		folderService.add(subFolder);
		
		subFolder = new FolderDTO();
		subFolder.setFolderName("test folder" + new Random().nextInt());
		subFolder.setFolderType(FolderTypeEnum.DB);
		subFolder.setParentId(f.getId());
		subFolder.setUserId(userId);
		subFolder.setGmtCreate(new Date());
		subFolder.setGmtModified(f.getGmtCreate());
		folderService.add(subFolder);
		
		
		FolderDTO subsubFolder = new FolderDTO();
		subsubFolder.setFolderName("test folder" + new Random().nextInt());
		subsubFolder.setFolderType(FolderTypeEnum.DB);
		subsubFolder.setParentId(subFolder.getId());
		subsubFolder.setUserId(userId);
		subsubFolder.setGmtCreate(new Date());
		subsubFolder.setGmtModified(f.getGmtCreate());
		folderService.add(subsubFolder);
		subFolder.setFolderType(FolderTypeEnum.SSH);
		try {
			int removed = folderService.remove(subFolder);
			Assert.fail();
		} catch (BusinessException e) {
			Assert.assertEquals(ExceptionCodeEnum.FOLDER_NOT_EXISTS, e.getExceptionCode());
		}
	}
	
	
	@Test
	public void 异常系_删除文件夹_删除根目录() throws BusinessException {
		String userId = UUID.randomUUID().toString();
		
		FolderDTO f = new FolderDTO();
		f.setFolderName("test folder" + new Random().nextInt());
		f.setFolderType(FolderTypeEnum.DB);
		f.setParentId("0");
		f.setUserId(userId);
		f.setGmtCreate(new Date());
		f.setGmtModified(f.getGmtCreate());
		folderService.add(f);
		

		try {
			int removed = folderService.remove(f);
			Assert.fail();
		} catch (BusinessException e) {
			Assert.assertEquals(ExceptionCodeEnum.FOLDER_ROOT_CANNOT_BE_REMOVED, e.getExceptionCode());
		}
	}
	
	@Test
	public void 正常系_重命名() throws BusinessException {
		String userId = UUID.randomUUID().toString();
		
		FolderDTO f = new FolderDTO();
		f.setFolderName("test folder" + new Random().nextInt());
		f.setFolderType(FolderTypeEnum.DB);
		f.setParentId("0");
		f.setUserId(userId);
		f.setGmtCreate(new Date());
		f.setGmtModified(f.getGmtCreate());
		folderService.add(f);
		
		FolderDTO subFolder = new FolderDTO();
		subFolder.setFolderName("test folder" + new Random().nextInt());
		subFolder.setFolderType(FolderTypeEnum.DB);
		subFolder.setParentId(f.getId());
		subFolder.setUserId(userId);
		subFolder.setGmtCreate(new Date());
		subFolder.setGmtModified(f.getGmtCreate());
		folderService.add(subFolder);
		subFolder.setFolderName("房价未来房价连进来访问" + new Random().nextInt());
		folderService.rename(subFolder);
		
	}
}
