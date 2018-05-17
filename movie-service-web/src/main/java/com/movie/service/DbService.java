package com.movie.service;

public interface DbService {

	/**
	 * 爬取数据保存进入数据库---97电影
	 */
	void save97MovieToDb();

	public void update97MovieToDb(int beg,int end);
	
	/**
	 * 爬取电影天堂的最新电影 
	 */
	void saveDyttMovieToDb(String type, int beg, int end, String begUrl);

	/**
	 * 更新电影天堂的电影
	 */
	int updateMovieFromDytt(String type,int endPage,String begUrl);

}