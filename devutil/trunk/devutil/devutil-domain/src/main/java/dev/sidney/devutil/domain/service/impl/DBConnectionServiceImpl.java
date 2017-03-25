/**
 * 
 */
package dev.sidney.devutil.domain.service.impl;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.sidney.devutil.domain.dto.DBConnectionDTO;
import dev.sidney.devutil.domain.exception.BusinessException;
import dev.sidney.devutil.domain.exception.ExceptionCodeEnum;
import dev.sidney.devutil.domain.service.DBConnectionService;
import dev.sidney.devutil.store.dao.CommonDAO;
import dev.sidney.devutil.store.model.db.DBConnection;

/**
 * @author 杨丰光 2015年9月18日11:26:49
 *
 */
@Service
public class DBConnectionServiceImpl extends BaseService implements DBConnectionService {

	private static final Logger logger = LoggerFactory.getLogger(DBConnectionServiceImpl.class);
	
	@Resource(name="commonDao")
	private CommonDAO dao;
	
	@Override
	@Transactional(value="transactionManager", rollbackFor=BusinessException.class)
	public void add(DBConnectionDTO dto) throws BusinessException {
		DBConnection conn = new DBConnection();
		conn.setCharset(dto.getCharset());
		conn.setDbType(dto.getDbType());
		conn.setGmtCreate(new Date());
		conn.setGmtModified(conn.getGmtCreate());
		conn.setHost(dto.getHost());
		conn.setName(dto.getName());
		conn.setPassword(dto.getPassword());
		conn.setPort(dto.getPort());
		conn.setServicename(dto.getServicename());
		conn.setSid(dto.getSid());
		conn.setUsername(dto.getUsername());
		conn.setUserid(dto.getUserid());
		conn.setDbname(dto.getDbname());
		dao.insert(conn);
	}
	
	@Override
	public List<DBConnectionDTO> getListByUserId(String userId) {
		DBConnection conn = new DBConnection();
		conn.setUserid(userId);
		List<DBConnection> list = dao.queryForList(conn);
		List<DBConnectionDTO> resList = new ArrayList<DBConnectionDTO>();
		for (DBConnection record: list) {
			resList.add(voToBo(record));
		}
		return resList;
	}

	private DBConnectionDTO voToBo(DBConnection conn) {
		DBConnectionDTO dto = new DBConnectionDTO();
		dto.setCharset(conn.getCharset());
		dto.setDbType(conn.getDbType());
		dto.setGmtCreate(conn.getGmtCreate());
		dto.setGmtModified(conn.getGmtModified());
		dto.setHost(conn.getHost());
		dto.setName(conn.getName());
		dto.setPassword(conn.getPassword());
		dto.setPort(conn.getPort());
		dto.setServicename(conn.getServicename());
		dto.setSid(conn.getSid());
		dto.setUsername(conn.getUsername());
		dto.setUserid(conn.getUserid());
		dto.setDbname(conn.getDbname());
		return dto;
	}
	
	private String getConnectionStr(DBConnectionDTO dto) {
		String str = "";
		if ("ORACLE".equals(dto.getDbType())) {
			str = String.format("jdbc:oracle:thin:@%s:%d%s", dto.getHost(), dto.getPort(), StringUtils.isEmpty(dto.getSid()) ? ("/" + dto.getServicename()) : (":" + dto.getSid()));
		} else if ("MYSQL".equals(dto.getDbType())) {
			str = String.format("jdbc:mysql://%s:%d/%s?useUnicode=true&characterEncoding=utf8", dto.getHost(), dto.getPort(), dto.getDbname());
		}
		return str;
	}

	@Override
	public void testConnection(DBConnectionDTO dto) throws BusinessException {
		try {
			String connStr = getConnectionStr(dto);
			DriverManager.getConnection(connStr, dto.getUsername(), dto.getPassword());
		} catch (SQLException e) {
			logger.info("测试连接异常\n" + dto, e);
			throw new BusinessException(ExceptionCodeEnum.CONNECTION_TEST_FAILED, e, "测试连接失败");
		} catch (Exception e) {
			logger.info("测试连接异常\n" + dto, e);
			throw new BusinessException(ExceptionCodeEnum.CONNECTION_TEST_FAILED, e, "测试连接失败");
		}
	}
}
