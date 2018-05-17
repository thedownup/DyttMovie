package com.movie.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author zjt
 * @Description: 控制登录
 */
@Controller
public class LoginController {
	
	@Value("${loginusername}")
	private String USERNAME;
	@Value("${loginpassword}")
	private String PASSWORD;
	
	
	@RequestMapping("/login")
	public String login(@RequestParam(defaultValue="-1")String username,@RequestParam(defaultValue="-1")String password,HttpSession session){
		if (username.equals(USERNAME) && password.equals(PASSWORD)) {
			session.setAttribute("user", username);
		}
		return "redirect:index";
	}
	
	@RequestMapping("loginout")
	public String loginout(HttpServletRequest request){
		request.getSession().removeAttribute("user");
		return "redirect:./";
	}
}
