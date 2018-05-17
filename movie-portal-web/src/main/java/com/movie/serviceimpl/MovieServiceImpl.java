package com.movie.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.movie.dao.MovieDao;
import com.movie.pojo.Movie;
import com.movie.pojo.NObject;
import com.movie.service.MovieService;

/**
 * @author zjt
 * @Description: 查询电影
 */
@Service
public class MovieServiceImpl implements MovieService{

	@Autowired
	private MovieDao movieDao;
	
	@Override
	public Movie getMovieAndMovieInfo(int id) {
		return movieDao.getMovies(id);
	}

	@Override
	public NObject searchMovies(int page, int num, String q) {
		
		return movieDao.getMovies(page, num, q);
	}

	@Override
	public NObject getLikeMovies(int uid ,int page, int num) {
		return movieDao.getLikeMovies(uid, page, num);
	}
}
