package com.movie.actionimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.movie.pojo.PrivateMessage;
import com.movie.service.PrivateMessageService;
import com.movie.untils.JsonUtils;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

@Controller
public class PrivateMessageAction extends ActionSupport{
	
	@Autowired
	private PrivateMessageService privateMessageService;
	
	private String jsonData;
	private int uid;
	private int fid;
	private String message;
	
	public String getPrivateMessage(){
		
		List<PrivateMessage> privateMessage = privateMessageService.getPrivateMessage(uid, fid);
		jsonData = JsonUtils.objectToJson(privateMessage);
	
		return Action.SUCCESS;
	}
	
	public String sendMessage(){
		try {
			privateMessageService.sendMessage(uid, fid, message);
			jsonData = "发送了一条消息";
		} catch (Exception e) {
			jsonData = "发送失败";
		}
		return Action.SUCCESS;
	}
	
	public String getSystemMessage(){
		
		List<PrivateMessage> systemMessage = privateMessageService.getSystemMessage();
		jsonData = JsonUtils.objectToJson(systemMessage);
		
		return Action.SUCCESS;
	}
	
	
	public int getUid() {
		return uid;
	}

	public int getFid() {
		return fid;
	}
	
	public String getJsonData() {
		return jsonData;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public void setFid(int fid) {
		this.fid = fid;
	}
}
