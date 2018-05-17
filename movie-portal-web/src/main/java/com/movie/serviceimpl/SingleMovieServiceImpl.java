package com.movie.serviceimpl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.movie.dao.SingleMovieDao;
import com.movie.pojo.FilterTag;
import com.movie.pojo.Movie;
import com.movie.pojo.NObject;
import com.movie.service.SingleMovieService;

@Service
public class SingleMovieServiceImpl implements SingleMovieService{

	@Autowired
	private SingleMovieDao singleMovieDao;
	
	
	@Override
	public NObject getMovies(int page, int num,FilterTag filterTag) {
		return singleMovieDao.getMovies(page, num,filterTag);
	}


	@Override
	public int getCount() {
		return singleMovieDao.getCount();
	}


	@Override
	public List<Movie> getWonderfulMovies(String type) {
		
		StringBuffer types = new StringBuffer("");

		if (type == null || type.equals("")) {
			types.append("like '%'");
		}else if (type.indexOf("/") != -1) {
			String[] split = type.split("/");
			for (int i = 0; i < split.length; i++) {
				if (i == 0) {
					types.append("like  '%"+split[i]+"%'");
				} else {
					types.append(" or m.type like '%"+split[i]+"%'");
				} 
			}
		} else {
			String[] split = type.split(" ");
			for (int i = 0; i < split.length; i++) {
				if (i == 0) {
					types.append("like '%"+split[i]+"%'");
				} else {
					types.append(" or m.type like '%"+split[i]+"%'");
				} 
			}
		}
		
		return singleMovieDao.getWonderfulMovies(types.toString());
	}


	@Override
	public Map<Integer, String> getRecentlyMovie() {
		return singleMovieDao.getRecentlyMovie();
	}

}
