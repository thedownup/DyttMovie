package com.movie.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.movie.pojo.HotMovie;
import com.movie.redis.HotMovieRedis;
import com.movie.redis.RecentlyRedis;
import com.movie.service.RedisService;

import redis.clients.jedis.JedisPool;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/spring/*.xml"})
public class RedisTest {
	
	@Autowired
	private RedisService redisService;
	@Autowired
	private HotMovieRedis hotMovieRedis;
	@Autowired
	private RecentlyRedis recentlyRedis;
	@Autowired
	private JedisPool jedisPool;
	
	@Test
	public void testRedis() throws InterruptedException{
		for (int i = 0; i < 11; i++) {
			redisService.saveRecentlyMovies(1, 12+new Random().nextInt(), "aa22aaa");
		}
		/*for (int i = 0; i < 111; i++) {
			
			redisService.saveRecentlyMovies(1, 22, "aaaaa");
		}*/
		redisService.getRecentlyMovies(1);
	}
	
	
	@Test
	public void testHotMovie(){
		List<HotMovie> hotMovie = hotMovieRedis.getHotMovie();
	}
	
	@Test
	public void testRecently(){
		HashMap<String,String> hashMap = recentlyRedis.getRecentlyWatchUser("958");
		Set<Entry<String,String>> entrySet = hashMap.entrySet();
		for (Entry<String, String> entry : entrySet) {
			System.out.println(entry.getKey() + "  " + entry.getValue());
		}
	}
	
	@Test
	public void getNum(){
		System.out.println(jedisPool.getResource().hget("asdasd", "sadsd") == null);
	}
	
	@Test
	public void testRecordUser(){
		for (int i = 0; i < 500; i++) {
			new Thread(() ->{
				try {
					System.out.println(Thread.currentThread().getName());
					recentlyRedis.recordUser("21856", "219");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}).start();;
		}
	}
	
	@Test
	public void test(){
		System.out.println(jedisPool.getResource().hget("RecentlyWatch","42").equals(""));
		System.out.println(jedisPool.getResource().hlen("RecentlyWatch") != 0);
	}
	
	@Test
	public void getRecentlyMovie(){
		List<String> recentlyMovies = redisService.getRecentlyMovies(218);
		recentlyMovies.forEach(System.out::println);
	}
	
}
