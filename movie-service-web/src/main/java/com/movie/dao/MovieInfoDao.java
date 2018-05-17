package com.movie.dao;

import com.movie.pojo.EasyUIDataGridResult;
import com.movie.pojo.MovieInfo;

public interface MovieInfoDao extends BaseDao<MovieInfo>{
	
	public EasyUIDataGridResult getMovieInfos(int page,int num);

	public void deleteById(int... ids);

	public void updateMovieInfo(MovieInfo movieInfo);
	
	public MovieInfo getMovieInfoById(int id);
	
	public EasyUIDataGridResult getMovieInfoByMovieId(int mid);
}
