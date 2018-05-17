package com.movie.service;


import java.util.List;
import java.util.Map;

import com.movie.pojo.FilterTag;
import com.movie.pojo.Movie;
import com.movie.pojo.NObject;

public interface SingleMovieService {
	
	public int getCount();
	
	public NObject getMovies(int page,int num,FilterTag filterTag);
	
	public List<Movie> getWonderfulMovies(String type);
	
	public Map<Integer, String> getRecentlyMovie();
	
}
