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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/spring/*.xml"})
public class RedisTest {
	
	@Autowired
	private RollingRedis rollingRedis;
	@Autowired
	private HotMovieRedis hotMovieRedis;
	
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
		for (int i = 0; i < 1; i++) {
			
			HotMovie hotMovie = new HotMovie();
			hotMovie.setId(2);
			hotMovie.setScore("55");
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
}
