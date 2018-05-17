package com.movie.dao;

import com.movie.pojo.PageFlag;

public interface PageFlagDao extends BaseDao<PageFlag> {
	 int getLastPage(String movieType,String url);
	 
	 void setPage(String movieType,int page);
}
