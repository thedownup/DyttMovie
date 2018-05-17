package com.movie.service;

import java.util.List;

import com.movie.pojo.Rolling;

public interface HomeService {
	
	public List<Rolling> getRolling(int page,int num);
	public void updateRolling();
	public void saveRolling();
	
	
}
