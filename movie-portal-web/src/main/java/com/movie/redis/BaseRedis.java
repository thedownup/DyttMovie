package com.movie.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Component
@Lazy(true)
public class BaseRedis {
	@Autowired
	private JedisPool jedisPool;
	
	private final int MAXNUM = 20;
	private final long CONNECTTIMEOUT = 10000;
	
	protected Jedis getJedis(){
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		jedisPoolConfig.setMaxTotal(MAXNUM);
		jedisPoolConfig.setMaxWaitMillis(CONNECTTIMEOUT);
		return jedisPool.getResource();
	}
	
}
