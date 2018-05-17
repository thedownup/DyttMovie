package com.movie.service;

import com.movie.pojo.User;

public interface UserService {
	
	public User checkUser(User user);
	
	public String createUser(User user);
	
	public void update(User user);
	
	public void sendMail(User user,String token) throws Exception;
	
	public User getByName(String username);
	
	public String getUserTouXiangById(String id);
	
	public User getUserById(int uid);
	
	public boolean checkUserName(String userName);
}
