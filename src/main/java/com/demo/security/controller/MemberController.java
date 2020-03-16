package com.demo.security.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.demo.security.auth.model.Account;
import com.demo.security.auth.service.UserServiceImpl;

@Controller
public class MemberController {
	protected Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private UserServiceImpl userService;
	
	/**
	 * initial
	 */
	@RequestMapping(value = {"/", "/login"}, method = RequestMethod.GET)
	public String login(Model model) {
		return "login";
	}
	
	/**
	 * Registration view
	 */
	@RequestMapping(value = "/registration", method = RequestMethod.GET)
	public String registration(Model model) {
		model.addAttribute("user", new Account());
		return "registration";
	}
	
	/**
	 * Registration form
	 */
	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public String createNewUser(Model model, @Valid Account user, BindingResult bindingResult) {
		try {
			//< set the user information
			model.addAttribute("user", new Account());
			
			//< check the user name already exist or not
			Account userExists = userService.getUserByUsername(user.getUsername());
			if(userExists != null) {
				bindingResult.rejectValue("username", "error.user", "There is already a user registered with the user name provided");
			}
			
			if(bindingResult.hasErrors()) {
				log.error("[ykson] : " + bindingResult.getFieldError().toString());
			}
			else {
				//< save the user information
				userService.setUser(user);
				
				model.addAttribute("successMessage", "User has been registered successfully");
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			model.addAttribute("successMessage", "FAIL : " + e.getMessage());
		}
		
		return "registration";
	}
	
	/**
	 * Administration home
	 */
	@RequestMapping(value = "/admin/home", method = RequestMethod.GET)
	public String home(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Account user = null;
		try {
			user = userService.getUserByUsername(auth.getName());
		} catch (Exception e) {
			log.error("[ykson]" + e.getMessage());
		}
		
		log.debug("[ykson] User information : " + auth.getAuthorities().toString());
		
		model.addAttribute("username", "Welcome " + user.getUsername() + " (" + user.getEmail() + ")");
		model.addAttribute("adminMessage", "Content Available Only for Users with Admin Role");
		
		return "admin/home";
	}
}








