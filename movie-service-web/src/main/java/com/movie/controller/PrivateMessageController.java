package com.movie.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.movie.pojo.EasyUIDataGridResult;
import com.movie.pojo.MResult;
import com.movie.service.PrivateMessageService;

@Controller
@RequestMapping("/privatemessage")
public class PrivateMessageController {
	
	@Autowired
	private PrivateMessageService privateMessageService;
	
	@RequestMapping("/get")
	@ResponseBody
	public EasyUIDataGridResult getPrivateMessages(@RequestParam(defaultValue="1")int page,@RequestParam(defaultValue="10")int rows){
		return privateMessageService.getPrivateMessage(page,rows);
	}
	
	@RequestMapping("/send")
	@ResponseBody
	public MResult sendPrivateMessage(String message){
		try {
			privateMessageService.sendSystemPrivateMessage(message);
			return MResult.build("发送成功");
		} catch (Exception e) {
			return MResult.build("发送失败" + e.getMessage());
		}
	}
}
