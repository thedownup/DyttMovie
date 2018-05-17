package com.movie.dao;


import com.movie.pojo.Movie;
import com.movie.pojo.NObject;

public interface MovieDao extends BaseDao<Movie>{
	
	public Movie getMovies(int id);
	
	public NObject getMovies(int page,int num,String q);
	
	public NObject getLikeMovies(int uid,int page,int num);
	
}
