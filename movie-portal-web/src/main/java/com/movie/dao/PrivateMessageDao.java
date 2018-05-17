package com.movie.dao;

import java.util.List;

import com.movie.pojo.PrivateMessage;
import com.movie.pojo.User;

public interface PrivateMessageDao {
	
	public List<PrivateMessage> getPrivateMessage(int uid,int fid);
	
	public void sendMessage(int uid,int fid,String message);
	
	public List<User> getRecentlyUser(int uid);
	
	public List<PrivateMessage> getSystemMessage();
	
}
