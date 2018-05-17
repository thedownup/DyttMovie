package com.movie.service;


import com.movie.pojo.Movie;
import com.movie.pojo.NObject;

public interface MovieService {

	public Movie getMovieAndMovieInfo(int id);
	
	public NObject searchMovies(int page,int num,String q);	
	
	public NObject getLikeMovies(int uid,int page, int num);
}
