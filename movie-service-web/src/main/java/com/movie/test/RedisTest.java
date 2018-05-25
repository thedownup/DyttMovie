package com.movie.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.movie.pojo.EasyUIDataGridResult;
import com.movie.pojo.HotMovie;
import com.movie.pojo.Rolling;
import com.movie.reids.HotMovieRedis;
import com.movie.reids.RollingRedis;
import com.movie.until.JsonUtils;

import redis.clients.jedis.JedisPool;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/spring/*.xml"})
public class RedisTest {
	
	@Autowired
	private RollingRedis rollingRedis;
	@Autowired
	private HotMovieRedis hotMovieRedis;
	@Autowired
	private JedisPool jedisPool;
	
	@Test
	public void testSave(){
		for (int i = 0; i < 11; i++) {
			Rolling rolling = new Rolling();
			rolling.setBigImageUrl("aa");
			rolling.setMid(1);
			System.out.println();
			rollingRedis.saveRolling(rolling);
		}
	}
	
	@Test
	public void get(){
		EasyUIDataGridResult rolling = rollingRedis.getRolling();
		System.out.println(JsonUtils.objectToJson(rolling));
	}
	
	@Test
	public void testUpdate(){
		Rolling rolling = new Rolling();
		rolling.setId(7);
		rolling.setBigImageUrl("xxxxxxxxxxx");
		rolling.setMid(222222222);
		rollingRedis.updateRolling(rolling);
	}
	
	@Test
	public void testDelete(){
		rollingRedis.deleteRolling(33);
	}
	
	@Test
	public void testHotMovie(){
		for (double i = 0; i < 50; i++) {
			
			HotMovie hotMovie = new HotMovie();
			hotMovie.setId(Integer.valueOf(String.valueOf(i)));
			hotMovie.setScore(String.valueOf(i));
			hotMovie.setMovieName("11");
			hotMovie.setMovie(false);
			hotMovie.setYear("");
			hotMovie.setHid(0);
			
			try {
				hotMovieRedis.saveHotMovie(hotMovie);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}
	
	@Test
	public void testHotMovieDelete(){
		
		HotMovie hotMovie = new HotMovie();
		hotMovie.setId(2);
		hotMovie.setScore("2");
		hotMovie.setMovieName("My Pet Dinosaur ");
		hotMovie.setMovie(false);
		hotMovie.setMovieImgUrl("http://www.imageto.org/images/3KAow.jpg");
		hotMovie.setYear("");
		hotMovie.setHid(Long.parseLong("1525618205698"));
		
		
		System.out.println(JsonUtils.objectToJson(hotMovie));
		
		hotMovieRedis.deleteHotMovie(hotMovie);
	}
	
	@Test
	public void test(){
		jedisPool.getResource().zremrangeByRank("HotMovie", 2, 3) ;
	}
	
	@Test
	public void testRedis(){
		jedisPool.getResource().set("sssss"+":"+"ass", "c");
		System.err.println(jedisPool.getResource().get("sssss"+":"+"ass"));
	}
	
	@Test
	public void test4(){
		jedisPool.getResource().del("RM"+String.valueOf("6"));
	}
}
