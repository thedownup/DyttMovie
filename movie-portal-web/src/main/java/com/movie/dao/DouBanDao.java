package com.movie.dao;

import java.util.List;

import com.movie.pojo.DouBan;

public interface DouBanDao extends BaseDao<DouBan>{
	
	public List<DouBan> getDouBan(int page);
	
}
