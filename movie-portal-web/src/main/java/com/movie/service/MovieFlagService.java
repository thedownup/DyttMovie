package com.movie.service;

import com.movie.pojo.MovieFlag;

public interface MovieFlagService {

	public void saveMovieFlag(int uid,int mid,MovieFlag movieFlag);
	public MovieFlag get(int uid,int mid);
	public int getLikeSee(int mid);
	public int getWantSee(int mid);
	public int getSeeBefore(int mid);
	
}
