package com.movie.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.movie.dao.UserDao;
import com.movie.pojo.EasyUIDataGridResult;
import com.movie.pojo.User;
import com.movie.service.UserService;
import com.movie.until.JsonUtils;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserDao userDao;

	@Override
	public String getUsers(int page, int num) {
		EasyUIDataGridResult easyUIDataGridResult = userDao.getUsers(page, num);
		String json = JsonUtils.objectToJson(easyUIDataGridResult);
		return json;
	}


	@Override
	public void deleteById(int id) {
		userDao.delete(id);
	}

	@Override
	public void update(User user) {
		userDao.update(user);
	}


	@Override
	public boolean haveUserName(String userName) {
		return userDao.haveUserName(userName);
	}


	@Override
	public void save(User user) {
		userDao.save(user);
	}


	@Override
	public void updateBackImg(int userId, String url) {
		userDao.updateBackImg(userId, url);
	}


	@Override
	public void updateTouxians(int userId, String url) {
		userDao.updateTouxians(userId, url);
	}

}
