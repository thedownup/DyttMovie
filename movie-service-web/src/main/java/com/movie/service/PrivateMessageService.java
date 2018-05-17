package com.movie.service;

import com.movie.pojo.EasyUIDataGridResult;

public interface PrivateMessageService {
	
	
	public void sendSystemPrivateMessage(String message);
	
	public EasyUIDataGridResult getPrivateMessage(int page,int num);
	
}
