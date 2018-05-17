package com.movie.task;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.movie.controller.DataController;
import com.movie.service.MovieService;


/**
 * 
 * @author zjt
 * @Description: 用于更新电影资源
 */
@Component
public class DataTask {
	
	private static final Logger logger = Logger.getLogger(DataTask.class);

	@Autowired
	private DataController dataController;
	@Autowired
	private MovieService movieService;
	
	/**
	 * 更新电影天堂的电影 每四天一次
	 */
	@Scheduled(cron="0 0 0 1/4 * ? ")
	public void updateDyttMovies(){
		logger.info("定时更新电影天堂的电影");
		
		dataController.updateDyttChinaMovie();
		dataController.updateDyttNewMovie();
		dataController.updateDyttOouMeiMovie();
	}
	
	/**
	 * 更新九七电影
	 */
	@Scheduled(cron="0 10 0 1/4 * ? ")
	public void updateJiuQiMovies(){
		logger.info("定时更新九七电影 的电影");
		dataController.updateJiuQiMovie();
	}
	
	/**
	 * 合并 
	 */
	@Scheduled(cron="0 15 0 1/4 * ? ")
	public void merge(){
		logger.info("定时合并重复电影");
		movieService.merge();
	}
	
	/**
	 * 合并 
	 */
	@Scheduled(cron="0 15 0 1/4 * ? ")
	public void clear(){
		logger.info("定时清理重复的链接");
		movieService.clearSameLinks();
	}
	
	/**
	 * 更新豆瓣
	 */
	@Scheduled(cron="0 20 0 1/4 * ? ")
	public void updateDouban(){
		logger.info("定时更新豆瓣");
		dataController.updateDouban();
	}
	
}
