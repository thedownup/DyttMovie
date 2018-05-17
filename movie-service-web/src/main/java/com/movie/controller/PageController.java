package com.movie.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author zjt
 * @Description: 控制easyui的所有页面
 */
@Controller
public class PageController {
	
	@RequestMapping("/index")
	public String index(HttpServletResponse response, HttpServletRequest request) throws IOException, ServletException{
		
		return "ManagerSystem";
	}
	
	/**
	 * 展示首页
	 * @return
	 */
	@RequestMapping("/sindex.html")
	public String showIndex(){
		return "index";
	}
	
	@RequestMapping("/{url}.html")
	public String showMovie(@PathVariable("url")String url){
		return url;
	}
	
	@RequestMapping("/usermanager.html")
	public String userManager(){
		return "usermanager";
	}
	
}
