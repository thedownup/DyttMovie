package com.movie.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.movie.dao.DouBanDao;
import com.movie.pojo.DouBan;
import com.movie.service.DouBanService;

@Service
public class DouBanServiceImpl implements DouBanService{

	@Autowired
	private DouBanDao douBanDao;
	
	@Override
	public List<DouBan> getDouBan(int page) {
		
		return douBanDao.getDouBan(page);
	}

}
