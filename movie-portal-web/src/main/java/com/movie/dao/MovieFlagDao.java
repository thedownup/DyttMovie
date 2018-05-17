package com.movie.dao;

import com.movie.pojo.MovieFlag;

public interface MovieFlagDao {

	public void save(int uid,int mid,MovieFlag movieFlag);
	
	public void delete(int uid,int id);
	
	public MovieFlag get(int uid,int mid);
	
	public int getLikeSee(int mid);
	public int getWantSee(int mid);
	public int getSeeBefore(int mid);
	
}
