package com.movie.redis;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.movie.pojo.HotMovie;
import com.movie.service.MovieService;
import com.movie.untils.JsonUtils;

import redis.clients.jedis.Jedis;

/**
 * @author zjt
 * @Description: 设置最近热门电影 用redis有序集合
 */
@Repository
public class HotMovieRedis extends BaseRedis{
	
	@Autowired
	private MovieService movieService;
	
	private static final String HOTMOVIENAME = "HotMovie";

	/**
	 * 获得所有的热门电影 (最多取50个)
	 * @return
	 */
	public List<HotMovie> getHotMovie(){

		Jedis jedis = getJedis();
		
		Set<String> zrevrange = jedis.zrangeByScore(HOTMOVIENAME, 0, 9999);
		
		List<HotMovie> lists = new ArrayList<HotMovie>();
		
		int count = 0;
		//最多显示50条
		for (String value : zrevrange) {
			count++;
			HotMovie hotMovie = JsonUtils.jsonToPojo(value, HotMovie.class);
			lists.add(hotMovie);
			if (count > 50) {
				break;
			}
		}
		jedis.close();
		return lists;
	}
}
