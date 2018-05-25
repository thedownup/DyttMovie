package com.movie.test;

import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.movie.controller.DataController;
import com.movie.controller.HotMovieController;
import com.movie.pojo.EasyUIDataGridResult;
import com.movie.pojo.HotMovie;
import com.movie.service.DbService;
import com.movie.service.DouBanService;
import com.movie.wangye.BaseDyttOrigin;
import com.movie.wangye.JiuqiOrigin;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/spring/*.xml"})
public class ControllerTest {
	
	@Value("${loginusername}")
	private String USERNAME;
	@Value("${loginpassword}")
	private String PASSWORD;
	@Autowired
	private DataController dataController;
	@Autowired
	private DbService dbService;
	@Autowired
	private BaseDyttOrigin baseDyttOrigin;
	@Autowired
	private DouBanService douBanService;
	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private JiuqiOrigin jiuqiOrigin;
	@Autowired
	private HotMovieController hotMovieController;
	
	@Test
	public void testDytt9(){
		dataController.saveDyttMovieToDb1();
	}
	
	@Test
	public void test97dy(){
		dbService.save97MovieToDb();
	}
	
	/*@Test
	public void test(){
		dyttTest.f();
	}*/
	
	@Test
	public void getPage(){
		int page = baseDyttOrigin.getPage("http://www.ygdy8.net/html/gndy/china/index.html");
	}
	
	@Test
	public void testDouBan(){
		douBanService.updateDouBan();
	}
	@Test
	public void testSessionFactory(){
		sessionFactory.openSession().getTenantIdentifier();
	}
	
	@Test
	public void testUpdate1(){
		dataController.updateDyttChinaMovie();
	}
	
	@Test
	public void testUpdate2(){
		dataController.updateDyttNewMovie();
	}
	
	@Test
	public void testUpdate3(){
		dataController.updateDyttOouMeiMovie();
	}
	
	@Test
	public void testJiuqi(){
		dataController.updateJiuQiMovie();
	}
	
	@Test
	public void test(){
		System.out.println(USERNAME+" "+PASSWORD);
	}
	
	@Test
	public void testJiqi(){
		dataController.updateJiuQiMovie();
	}
	
	@Test
	public void addHotmoive(){
		for (double j = 1; j <= 50; j++) {
			HotMovie hotMovie = new HotMovie();
			hotMovie.setScore(j+"");
			hotMovie.setId((int)j);
			hotMovieController.saveHotMovie(hotMovie);
		}
		hotMovieController.getHotMovies();
	}
	
	@Test
	public void testHotMovieGet(){
		EasyUIDataGridResult hotMovies = hotMovieController.getHotMovies();
		System.out.println(hotMovies.getRows().size());
	}
	
}
