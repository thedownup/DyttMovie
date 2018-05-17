package com.movie.dao;

import com.movie.pojo.EasyUIDataGridResult;
import com.movie.pojo.Movie;

public interface MovieDao extends BaseDao<Movie>{

	public EasyUIDataGridResult getMovies(int page,int num);

	public void deleteById(int... ids);

	public void updateMovie(Movie movie);
	
	public Movie getMovieById(int id);
	
	public void merge();

	public void clearSameLinks();
}
