/**
 * 
 */
package dev.sidney.devutil.web.controller;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import dev.sidney.devutil.domain.dto.DBConnectionDTO;
import dev.sidney.devutil.domain.dto.UserDTO;
import dev.sidney.devutil.domain.exception.BusinessException;
import dev.sidney.devutil.domain.service.DBConnectionService;
import dev.sidney.devutil.web.form.NewConnectionForm;

/**
 * @author 杨丰光 2015年9月18日13:52:46
 *
 */
@RequestMapping(value="/db")
@Controller
public class DBConnectionController extends BaseController {
	
	private static final Logger logger = LoggerFactory.getLogger(DBConnectionController.class);
	
	@Resource
	private DBConnectionService dbConnectionService;

	@RequestMapping(value = "/newConnection.htm", method = RequestMethod.POST)
	public void newConnection(NewConnectionForm form, HttpServletRequest request, HttpServletResponse response) {
		UserDTO user = this.getUserDTO(request);
		DBConnectionDTO dto = parseDBConnectionDTO(form, user);
		JSONObject res = new JSONObject();
		try {
			dbConnectionService.add(dto);
			res.put("responseCode", "OK");
		} catch (BusinessException e) {
			logger.error("新建连接失败\n" + form, e);
			res.put("responseCode", "FAIL");
			res.put("message", e.getMessage());
		}
		
		try {
			response.getWriter().print(res);
		} catch (IOException e) {
			logger.error("写入结果异常", e);
		}
	}
	
	private DBConnectionDTO parseDBConnectionDTO(NewConnectionForm form, UserDTO user) {
		DBConnectionDTO dto = new DBConnectionDTO();
		dto.setCharset("utf-8");
		dto.setDbType(form.getDbType());
		dto.setHost(form.getHost());
		dto.setName(form.getName());
		dto.setPassword(form.getPassword());
		dto.setPort(Integer.parseInt(form.getPort()));
		dto.setServicename(form.getServicename());
		dto.setSid(form.getSid());
		dto.setDbname(form.getDbname());
		dto.setUserid(user.getId());
		dto.setUsername(form.getUsername());
		return dto;
	}
	
	@RequestMapping(value = "/testConnection.htm", method = RequestMethod.POST)
	public void testConnection(NewConnectionForm form, HttpServletRequest request, HttpServletResponse response) {
		UserDTO user = this.getUserDTO(request);
		DBConnectionDTO dto = parseDBConnectionDTO(form, user);
		JSONObject res = new JSONObject();
		try {
			dbConnectionService.testConnection(dto);
			res.put("responseCode", "OK");
		} catch (BusinessException e) {
			logger.error("测试连接失败\n" + form, e);
			res.put("responseCode", "FAIL");
			res.put("message", e.getMessage());
		}
		
		try {
			response.getWriter().print(res);
		} catch (IOException e) {
			logger.error("写入结果异常", e);
		}
	}
	
	@RequestMapping(value = "/getConnectionList.htm", method = RequestMethod.POST)
	public void getConnectionList(HttpServletRequest request, HttpServletResponse response) {
		UserDTO user = this.getUserDTO(request);
		List<DBConnectionDTO> list = dbConnectionService.getListByUserId(user.getId());
		
		JSONObject res = new JSONObject();
		res.put("list", list);
		res.put("responseCode", "OK");
		
		try {
			response.getWriter().print(res);
		} catch (IOException e) {
			logger.error("写入结果异常", e);
		}
	}
	
}
