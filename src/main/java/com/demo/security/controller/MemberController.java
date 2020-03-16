package com.demo.security.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.demo.security.auth.model.Account;
import com.demo.security.auth.service.UserService;

@Controller
public class MemberController {
	@Autowired
	private UserService userService;
	
	/**
	 * initial
	 */
	@RequestMapping(value = {"/", "/login"}, method = RequestMethod.GET)
	public ModelAndView login() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("login");
		
		return modelAndView;
	}
	
	/**
	 * Registration
	 */
	@RequestMapping(value = "/registration", method = RequestMethod.GET)
	public ModelAndView registration() {
		ModelAndView mav = new ModelAndView();
		Account user = new Account();
		mav.addObject("user", user);
		mav.setViewName("registration");
		
		return mav;
	}
	
	/**
	 * Registration
	 */
	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public ModelAndView createNewUser(@Valid Account user, BindingResult bindingResult) {
		ModelAndView mav = new ModelAndView();
		Account userExists = userService.findUserByUserName(user.getUsername());
		if(userExists != null) {
			bindingResult.rejectValue("username", "error.user", "There is already a user registered with the user name provided");
		}
		
		if(bindingResult.hasErrors()) {
			mav.setViewName("registration");
		}
		else {
			userService.saveUser(user);
			mav.addObject("successMessage", "User has been registered successfully");
			mav.addObject("user", new Account());
			mav.setViewName("registration");
		}
		
		return mav;
	}
	
	/**
	 * Administration home
	 */
	@RequestMapping(value = "/admin/home", method = RequestMethod.GET)
	public ModelAndView home() {
		ModelAndView mav = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Account user = userService.findUserByUserName(auth.getName());
		mav.addObject("username", "Welcome " + user.getUsername() + " (" + user.getEmail() + ")");
		mav.addObject("adminMessage", "Content Available Only for Users with Admin Role");
		mav.setViewName("admin/home");
		
		return mav;
	}
}








