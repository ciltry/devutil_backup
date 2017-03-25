/**
 * 
 */
package dev.sidney.devutil.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;

import dev.sidney.devutil.domain.dto.UserDTO;

/**
 * 
 * @author 杨丰光 2015年8月19日18:48:47
 *
 */
@Controller
public class BaseController {

	protected UserDTO getUserDTO(HttpServletRequest request) {
		return (UserDTO) request.getSession().getAttribute("user");
	}
}
