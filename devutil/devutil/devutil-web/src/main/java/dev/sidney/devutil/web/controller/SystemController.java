/**
 * 
 */
package dev.sidney.devutil.web.controller;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import dev.sidney.devutil.domain.dto.UserDTO;
import dev.sidney.devutil.domain.exception.BusinessException;
import dev.sidney.devutil.domain.req.RegisterUserReq;
import dev.sidney.devutil.domain.service.UserService;
import dev.sidney.devutil.web.form.LoginForm;
import dev.sidney.devutil.web.form.RegisterForm;

/**
 * @author 杨丰光 2015年8月26日15:45:14
 *
 */
@Controller
public class SystemController {

	private static final Logger logger = LoggerFactory.getLogger(SystemController.class);
	
	@Resource
	private UserService userSerivce;
	
	@RequestMapping(value = "/login.htm", method = RequestMethod.GET)
	public String initLogin(ModelMap model) {
		return "system/login";
	}
	
	@RequestMapping(value = "/register.htm", method = RequestMethod.GET)
	public String initRegister(ModelMap model) {
		return "system/register";
	}
	
	@RequestMapping(value = "/workspace.htm", method = RequestMethod.GET)
	public String initWorkspace(ModelMap model) {
		return "workspace";
	}
	
	@RequestMapping(value = "/register.htm", method = RequestMethod.POST)
	public void register(RegisterForm form, HttpServletResponse response) {
		
		
		JSONObject res = new JSONObject();
		
		RegisterUserReq user = parseRegisterUserReq(form);
		try {
			userSerivce.register(user);
			res.put("responseCode", "OK");
		} catch (BusinessException e) {
			res.put("responseCode", "FAIL");
			res.put("message", e.getMessage());
		}
		
		try {
			response.getWriter().print(res);
		} catch (IOException e) {
			logger.error("写入结果异常", e);
		}
	}
	
	
	@RequestMapping(value = "/login.htm", method = RequestMethod.POST)
	public void login(LoginForm form, HttpServletRequest request, HttpServletResponse response) {
		UserDTO user = null;
		JSONObject res = new JSONObject();
		try {
			user = userSerivce.getUser(form.getUsername());
			if (user != null && user.getUsername().equals(form.getUsername()) && user.getPassword().equals(form.getPassword())) {
				res.put("responseCode", "OK");
				request.getSession().setAttribute("user", user);
			} else {
				res.put("responseCode", "FAIL");
				res.put("message", "用户名或密码错误");
			}
		} catch (BusinessException e) {
			res.put("responseCode", "FAIL");
			res.put("message", e.getMessage());
		}
		
		try {
			response.getWriter().print(res);
		} catch (IOException e) {
			logger.error("写入结果异常", e);
		}
	}
	
	private RegisterUserReq parseRegisterUserReq(RegisterForm form) {
		RegisterUserReq registerUserReq = new RegisterUserReq();
		registerUserReq.setNickname(form.getNickname());
		registerUserReq.setPassword(form.getPassword());
		registerUserReq.setUsername(form.getUsername());
		return registerUserReq;
	}
}
