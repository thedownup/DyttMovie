package com.movie.redis;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Component
@Lazy(true)
public class BaseRedis {
	
	@Autowired
	private JedisPool jedisPool;
	
	protected Jedis getJedis(){
		return jedisPool.getResource();
	}
	
}
