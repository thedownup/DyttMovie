package com.movie.service;

import com.movie.pojo.User;

public interface UserService {

	public String getUsers(int page,int num);
	
	public void deleteById(int id);

	public void update(User user);
	
	public boolean haveUserName(String userName);
	
	public void save(User user);
	
	public void updateBackImg(int userId,String url);
	
	public void updateTouxians(int userId,String url);
}
