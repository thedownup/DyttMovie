package com.movie.dao;

import com.movie.pojo.EasyUIDataGridResult;
import com.movie.pojo.User;

public interface UserDao extends BaseDao<User>{
	
	public EasyUIDataGridResult getUsers(int page,int num);
	
	public void deleteById(int id);
	
	public boolean haveUserName(String userName);
	
	public void updateBackImg(int userId,String url);
	
	public void updateTouxians(int userId,String url);
	
}

