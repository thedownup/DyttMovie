package com.movie.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.movie.dao.FocusUserDao;
import com.movie.pojo.FocusUser;
import com.movie.service.FocusUserService;

@Service
public class FocusUserServiceImpl implements FocusUserService{

	@Autowired
	private FocusUserDao focusUserDao;
	
	@Override
	public List<FocusUser> getFocusUser(int uid) {
		return focusUserDao.getFocusUser(uid);
	}

	@Override
	public void insertFocusUser(int uid, int fid) {
		focusUserDao.insertFocusUser(uid, fid);
	}

	@Override
	public void removeFocusUser(int uid, int fid) {
		focusUserDao.removeFocusUser(uid, fid);
	}

	@Override
	public boolean checkFocusUser(int uid,int fid) {
		return focusUserDao.checkFocusUser(uid, fid);
	}

}
