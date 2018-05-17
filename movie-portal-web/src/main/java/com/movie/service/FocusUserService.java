package com.movie.service;

import java.util.List;

import com.movie.pojo.FocusUser;

public interface FocusUserService {
	
	public List<FocusUser> getFocusUser(int uid);
	
	public void insertFocusUser(int uid,int fid);
	
	public void removeFocusUser(int uid,int fid);

	public boolean checkFocusUser(int uid,int fid);
}
