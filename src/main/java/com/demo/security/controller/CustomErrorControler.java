package com.demo.security.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class CustomErrorControler implements ErrorController {
	protected Logger log = LoggerFactory.getLogger(this.getClass());
	private final String DEFAULT_ERROR_PATH = "/error";
	
	////////////////////////////////////////////////////////////////////////////////
	//< public functions

	@Override
	public String getErrorPath() {
		return DEFAULT_ERROR_PATH;
	}
	
	@RequestMapping("/error")
	public String errorHandle(HttpServletRequest request, Model model) {
		return errorHandleImpl(request, model);
	}
	
	
	/**
	 * Access denied
	 */
	@RequestMapping(value = "/access-denied", method = RequestMethod.GET)
	public String accessDenied(Model model) {
		//< set the attributes
		model.addAttribute("errorCode", "403");
		model.addAttribute("errorMessage", "Forbidden");
		
		return getErrorPath() + "/error";
	}
	
	////////////////////////////////////////////////////////////////////////////////
	//< private functions
	
	/**
	 * Implement a error page
	 */
	private String errorHandleImpl(HttpServletRequest request, Model model) {
		//< get the status code
		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
		//< get the HTTP status
		HttpStatus httpStatus = HttpStatus.valueOf(Integer.valueOf(status.toString()));
		
		//< set the attributes
		model.addAttribute("errorCode", status.toString());
		model.addAttribute("errorMessage", httpStatus.getReasonPhrase());
		
		return getErrorPath() + "/error";
	}
}





