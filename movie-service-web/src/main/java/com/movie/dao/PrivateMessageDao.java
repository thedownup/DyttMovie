package com.movie.dao;

import com.movie.pojo.EasyUIDataGridResult;

public interface PrivateMessageDao {
	
	public void sendSystemPrivateMessage(String message);
	
	public EasyUIDataGridResult getPrivateMessage(int page,int num);
	
	
}
