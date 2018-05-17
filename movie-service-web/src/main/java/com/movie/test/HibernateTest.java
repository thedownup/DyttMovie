package com.movie.test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.movie.controller.UserController;
import com.movie.dao.MovieDao;
import com.movie.dao.MovieInfoDao;
import com.movie.dao.UserDao;
import com.movie.pojo.EasyUIDataGridResult;
import com.movie.pojo.Movie;
import com.movie.pojo.MovieInfo;
import com.movie.pojo.User;
import com.movie.service.DouBanService;
import com.movie.service.MovieService;
import com.movie.service.PrivateMessageService;
import com.movie.service.UserService;
import com.movie.until.JsonUtils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/spring/*.xml"})
public class HibernateTest {

	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private UserService userService;
	@Autowired
	private UserDao userDao;
	@Autowired
	private JedisPool jedisPool;
	@Autowired
	private MovieService movieService;
	@Autowired
	private MovieDao movieDao;
	@Autowired
	private MovieInfoDao movieInfoDao;
	@Autowired
	private DouBanService douBanService;
	@Autowired
	private UserController userController;
	@Autowired
	private PrivateMessageService privateMessageService;

	private Session session;
	@Before
	public void before(){
		session = sessionFactory.openSession();
		session.getTransaction().begin();
	}
	
	@After
	public void after(){
		session.getTransaction().commit();
		session.close();
		sessionFactory.close();
	}
	@Test
	public void test(){
		Session session = sessionFactory.getCurrentSession();
	}

	@Test
	public void testRedis(){
		Jedis jedis = jedisPool.getResource();
		jedis.set("a","vb");
	}

	@Test
	public void testCascade(){
		userService.deleteById(110);
	}

	@Test
	public void testSave(){
		for (int i = 0; i < 100; i++) {
			User user = new User();
			user.setBirthday(new Date());
			user.setCreated(new Date());
			user.setUpdated(new Date());
			user.setPassWord("1"+new Random().nextInt());
			user.setUserName("2"+new Random().nextInt());
			userDao.save(user);
		}
	}


	
	@Test
	public void testTime() throws ParseException{
		Session session = sessionFactory.openSession();
		Transaction transaction = session.getTransaction();
		transaction.begin();
		User user = (User) session.get(User.class,109);
		System.out.println(user.toString());
		String json = JsonUtils.objectToJson(user);
		System.out.println(json);
		 SimpleDateFormat sdf1=new SimpleDateFormat("yyyy/MM/dd");
		Date myDate1 = sdf1.parse("2011/06/01");
		System.out.println(myDate1);
		user.setBirthday(myDate1);
		user.setBackImg("1111111111111111111");
		session.update(user);
		transaction.commit();
		User user1 = (User) session.get(User.class,109);
		System.out.println(user1.toString());
	}
	
	@Test
	public void testDate2(){
		DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
		Date myDate1 = null;
		try {
			myDate1 = dateFormat1.parse("2009-06-01");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		User user = new User();
		user.setBirthday(myDate1);
		user.setCreated(myDate1);
		user.setUpdated(myDate1);
		user.setPassWord("1"+new Random().nextInt());
		user.setUserName("2"+new Random().nextInt());
		userDao.save(user);
	}
	
	@Test
	public void movieTest(){
		EasyUIDataGridResult movies = movieService.getMovies(2, 10);
		System.out.println(JsonUtils.objectToJson(movies));
	}
	
	@Test
	public void movieTestDel(){
		movieService.deleteById(2);
	}

	@Test
	public void updateMovie(){
		
		Movie movie = new Movie();
		movie.setId(17);
		movie.setMovieName("sssssssssssssssss");
		movieService.updateMovie(movie);
	}
	
	@Test
	public void testMovieInfo(){
		EasyUIDataGridResult easyUIDataGridResult = movieInfoDao.getMovieInfos(1, 10);
		System.out.println(JsonUtils.objectToJson(easyUIDataGridResult));
	}
	
	@Test
	public void testGet(){
		EasyUIDataGridResult movieInfoByMovieId = movieInfoDao.getMovieInfoByMovieId(2);
		System.out.println(movieInfoByMovieId.getTotal()+"~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println(JsonUtils.objectToJson(movieInfoByMovieId));
	}
	
	@Test
	public void testMovieInfoUpdate(){
		MovieInfo movieInfo = movieInfoDao.getMovieInfoById(5);
		System.out.println(movieInfo.toString());
		Movie movie = movieService.getMovieById(movieInfo.getId());
		movieInfo.setMovie(movie);
		movieInfo.setrTime("dsadadasd");
		movieInfoDao.update(movieInfo);
	}
	
	@Test
	public void testDouban(){
		douBanService.updateDouBan();
	}
	
	@Test
	public void testMerge(){
		movieDao.merge();
	}
	
	@Test
	public void testUpdate(){
		User user = (User) session.get(User.class, 219);
		user.setState(1);
		user.setSex((short)0);

		try {
			userService.update(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println(user.toString());
	}
	
	@Test
	public void testUser(){
		User user = new User();
		user.setId(243);
		user.setUserName("edededd");
		user.setPassWord("11ddbaf3386aea1f2974eee984542152");
		user.setBirthday(new Date());
		user.setSex((short)1);
		user.setEmail("632443784@qq.com");
		user.setCreated(new Date());
		user.setPassWord("dddd");
		user.setUpdated(new Date());
		System.out.println(JsonUtils.objectToJson(user));
		userController.update(user, "false");
	}
	
	@Test
	public void testUserController(){
		String json = jedisPool.getResource().get("c");
		User user = JsonUtils.jsonToPojo(json, User.class);
		user.setUserName("abcdef1g");
		userService.update(user);
	}
	
	@Test
	public void testPrivateMessage(){
		privateMessageService.sendSystemPrivateMessage("2018年5月16日00:45:24");
	}
	
/*	@Test
	public void testPrivateMessageGet(){
		List<PrivateMessage> privateMessage = privateMessageService.getPrivateMessage();
		System.out.println(privateMessage.size());
		for (PrivateMessage privateMessage2 : privateMessage) {
			System.out.println(privateMessage2.toString());
		}
		privateMessage.forEach((a) -> System.out.println(a.toString()));
	}*/
	
	@Test
	public void testPrivateMessage1(){
		privateMessageService.getPrivateMessage(2, 2);
	}
	
	@Test
	public void test3(){
		movieDao.clearSameLinks();
	}
	
	
}
