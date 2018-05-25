package com.movie.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.movie.pojo.RecentlyMovie;
import com.movie.redis.BaseRedis;
import com.movie.service.RedisService;
import com.movie.untils.JsonUtils;

import redis.clients.jedis.Jedis;

@Service
public class RedisServiceImpl extends BaseRedis implements RedisService{

	@Value("${RSIZE}")
	private int size;

	@Override
	public void saveRecentlyMovies(int uid,int mid,String movieName){
		Jedis jedis = getJedis();

		RecentlyMovie recentlyMovie = new RecentlyMovie();
		recentlyMovie.setMid(mid);
		recentlyMovie.setMovieName(movieName);
		List<String> lists = jedis.lrange("RM"+String.valueOf(uid), 0, -1);
		String json = JsonUtils.objectToJson(recentlyMovie);
		//去重
		jedis.ltrim("RM"+String.valueOf(uid), 0, 9);
		if(lists.size() == 0){
			jedis.lpush("RM"+String.valueOf(uid),json);
		}
		for (int i = 0; i < lists.size(); i++) {
			if (i+1 == lists.size() && !lists.get(i).equals(json)) {
				jedis.lpush("RM"+String.valueOf(uid),json);
			}
			
			if (lists.get(i).equals(json)) {
				break;
			}
		}
		jedis.close();
	}

	@Override
	public List<String> getRecentlyMovies(int uid){
		Jedis jedis = getJedis();
		//判断最近浏览的条数		
		long len = jedis.llen("RM"+String.valueOf(uid));
		if (len > size) {
			jedis.ltrim("RM"+String.valueOf(uid), 0, 9);
		}
		List<String> lists = jedis.lrange("RM"+String.valueOf(uid), 0, -1);
		
		jedis.close();
		return lists;
	}

}
