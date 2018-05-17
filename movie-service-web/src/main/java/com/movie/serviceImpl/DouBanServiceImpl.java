package com.movie.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.movie.dao.DouBanDao;
import com.movie.service.DouBanService;

@Service
public class DouBanServiceImpl implements DouBanService {

	@Autowired
	private DouBanDao douBanDao;
	
	@Override
	public void updateDouBan(){
		douBanDao.updatxDouBan();
	}
}
