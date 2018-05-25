package com.movie.reids;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.movie.pojo.EasyUIDataGridResult;
import com.movie.pojo.HotMovie;
import com.movie.pojo.Movie;
import com.movie.service.MovieService;
import com.movie.until.JsonUtils;

import redis.clients.jedis.Jedis;

/**
 * @author zjt
 * @Description: 设置最近热门电影 用redis有序集合
 */
@Component
public class HotMovieRedis extends BaseRedis{
	
	private static final Logger logger = Logger.getLogger(HotMovieRedis.class);
	
	@Autowired
	private MovieService movieService;
	
	private static final String HOTMOVIENAME = "HotMovie";

	/**
	 * 保存热门电影
	 * @param movie
	 * @param score
	 * @throws Exception 
	 */
	public void saveHotMovie(HotMovie hotMovie) throws Exception{

		Jedis jedis = getJedis();
		
		Movie movie = movieService.getMovieById(hotMovie.getId());
		
		
		if (movie == null) {
			throw new Exception("没有此id的电影");
		}
		
		//用来去重
		hotMovie.setHid(System.currentTimeMillis());
		hotMovie.setMovieName(movie.getMovieName());
		hotMovie.setMovieImgUrl(movie.getMovieImgUrl());
		hotMovie.setYear(movie.getYear());

		//设置属性
		
		if (movie.getType() != null || movie.getType().contains(" ")) {
			hotMovie.setType(movie.getType().replaceAll(" ", "/"));
		}
		hotMovie.setClarity(movie.getClarity());
		hotMovie.setArea(movie.getArea());
		
		String json = JsonUtils.objectToJson(hotMovie);
		jedis.zadd(HOTMOVIENAME, Double.parseDouble(hotMovie.getScore()), json);	
		jedis.close();
	}
	

	/**
	 * 更新热门电影
	 * @param rolling
	 */
	public void updateHotMovie(HotMovie hotMovie,double newScore){
		
		Jedis jedis = getJedis();
		Set<String> set = jedis.zrangeByScore(HOTMOVIENAME, hotMovie.getScore(), hotMovie.getScore());
		String json = JsonUtils.objectToJson(hotMovie);
		System.out.println(json);
		for (String value : set) {
			logger.debug(value);
			if (json.equals(value)) {
				jedis.zrem(HOTMOVIENAME, String.valueOf(hotMovie.getScore()),json);
				break;
			}
		}
		hotMovie.setScore(String.valueOf(newScore));
		jedis.zadd(HOTMOVIENAME, newScore,JsonUtils.objectToJson(hotMovie));
		jedis.close();
	}

	/**
	 * 删除
	 * @param id
	 */
	public void deleteHotMovie(HotMovie hotMovie){
		System.out.println(hotMovie.toString());
		Jedis jedis = getJedis();
		jedis.zrem(HOTMOVIENAME,String.valueOf(hotMovie.getScore()),JsonUtils.objectToJson(hotMovie));
		System.out.println(JsonUtils.objectToJson(hotMovie));
		jedis.close();
	}
	
	
	/**
	 * 获得所有的热门电影 (最多取50个)
	 * @return
	 */
	public EasyUIDataGridResult getHotMovie(){

		Jedis jedis = getJedis();
		
		EasyUIDataGridResult easyUIDataGridResult = new EasyUIDataGridResult();
		
		Set<String> zrevrange = jedis.zrangeByScore(HOTMOVIENAME, 0, 9999);
		
		List<HotMovie> lists = new ArrayList<HotMovie>();
		
		if (zrevrange.size() > 50) {
			jedis.zremrangeByRank(HOTMOVIENAME, 0, zrevrange.size()-51);
		}
		
		//最多显示50条
		for (String value : zrevrange) {
			HotMovie hotMovie = JsonUtils.jsonToPojo(value, HotMovie.class);
			lists.add(hotMovie);
		}
		
		easyUIDataGridResult.setRows(lists);
		easyUIDataGridResult.setTotal(lists.size());
		jedis.close();
		return easyUIDataGridResult;
	}
}
