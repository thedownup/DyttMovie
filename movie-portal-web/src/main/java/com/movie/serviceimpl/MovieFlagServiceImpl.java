package com.movie.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.movie.dao.MovieFlagDao;
import com.movie.pojo.MovieFlag;
import com.movie.service.MovieFlagService;

@Service
public class MovieFlagServiceImpl implements MovieFlagService{

	@Autowired
	private MovieFlagDao movieFlagDao;
	
	@Override
	public void saveMovieFlag(int uid,int mid,MovieFlag movieFlag) {
		
		movieFlagDao.save(uid, mid, movieFlag);
		
	}

	@Override
	public MovieFlag get(int uid, int mid) {
		
		return movieFlagDao.get(uid, mid);
	}

	@Override
	public int getLikeSee(int mid) {
		return movieFlagDao.getLikeSee(mid);
	}

	@Override
	public int getWantSee(int mid) {
		return movieFlagDao.getWantSee(mid);
	}

	@Override
	public int getSeeBefore(int mid) {
		return movieFlagDao.getSeeBefore(mid);
	}
	
}
