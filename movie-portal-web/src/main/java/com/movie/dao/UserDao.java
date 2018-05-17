package com.movie.dao;

import com.movie.pojo.User;

public interface UserDao extends BaseDao<User>{

	public User login(User user);
	public User getByName(String username);
	public String getUserTouXiangById(String id);
	public User getUserById(int uid);
	public boolean checkUserName(String userName);
}
