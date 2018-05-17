package com.movie.dao;


import java.util.List;
import java.util.Map;

import com.movie.pojo.FilterTag;
import com.movie.pojo.Movie;
import com.movie.pojo.NObject;
import com.movie.pojo.SingleMovie;

public interface SingleMovieDao extends BaseDao<SingleMovie>{
	
	public int getCount();
	
	public NObject getMovies(int page,int num,FilterTag filterTag);
	
	public List<Movie> getWonderfulMovies(String type);
	
	public Map<Integer, String> getRecentlyMovie();
}
