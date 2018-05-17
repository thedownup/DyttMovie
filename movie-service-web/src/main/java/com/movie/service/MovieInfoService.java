package com.movie.service;

import com.movie.pojo.EasyUIDataGridResult;
import com.movie.pojo.MovieInfo;

public interface MovieInfoService {
	
	public EasyUIDataGridResult getMovieInfos(int page,int num);

	public void deleteById(int... ids);

	public void updateMovieInfo(MovieInfo movieInfo);
	
	public void saveMovieInfo(MovieInfo movieInfo);
	
	public MovieInfo getMovieInfoById(int id);
	
	public EasyUIDataGridResult getMovieInfoByMovieId(int mid);
	
}
