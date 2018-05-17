package com.movie.service;

import java.util.List;

public interface RedisService {
	
	public void saveRecentlyMovies(int uid,int mid,String movieName);

	public List<String> getRecentlyMovies(int uid);
}
