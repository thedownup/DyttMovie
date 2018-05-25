package com.movie.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.movie.dao.CommentsDao;
import com.movie.dao.FocusUserDao;
import com.movie.dao.MovieDao;
import com.movie.dao.MovieFlagDao;
import com.movie.dao.PrivateMessageDao;
import com.movie.dao.SingleMovieDao;
import com.movie.dao.UserDao;
import com.movie.pojo.Comments;
import com.movie.pojo.DouBan;
import com.movie.pojo.FilterTag;
import com.movie.pojo.FocusUser;
import com.movie.pojo.Movie;
import com.movie.pojo.MovieFlag;
import com.movie.pojo.NObject;
import com.movie.pojo.PrivateMessage;
import com.movie.pojo.Reply;
import com.movie.pojo.ReplyInner;
import com.movie.pojo.SingleMovie;
import com.movie.pojo.User;
import com.movie.service.DouBanService;
import com.movie.service.MovieService;
import com.movie.service.SingleMovieService;
import com.movie.service.UserService;
import com.movie.untils.JsonUtils;

import redis.clients.jedis.JedisPool;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/spring/*.xml"})
public class HibernateTest {
	

	@Value("${touxian}")
	private String touxianurl;
	@Value("${beijin}")
	private String beijinurl;
	
	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private UserDao userDao;
	@Autowired
	private UserService userService;
	@Autowired
	private JedisPool jedisPool;
	@Autowired
	private MovieDao movieDao;
	@Autowired
	private SingleMovieDao singleMovieDao;
	@Autowired
	private MovieService movieService;
	@Autowired
	private CommentsDao commentsDao;
	@Autowired
	private MovieFlagDao movieFlagDao;
	@Autowired
	private DouBanService douBanService;
	@Autowired
	private FocusUserDao focusUserDao;
	@Autowired
	private PrivateMessageDao privateMessageDao;
	@Autowired
	private SingleMovieService singleMovieService;
	
	@Test
	public void testDb(){
		User user = new User();
		user.setUserName("21");
		user.setPassWord("21");
		user.setUpdated(new Date());
		user.setCreated(new Date());
		userDao.save(user);
	}
	
	@Test
	public void deleteTest(){
		User user = new User();
		user.setUserName("2");
		user.setPassWord("2");
		User user1 = userDao.login(user);
		if(user1 == null){
			System.out.println("null");
		}else {
			System.out.println("success");
			
		}
//		String password = user.getPassword();
	}
	
	@Test
	public void testUserService(){
		User user = new User();
		user.setUserName("xiaoa");
		user.setPassWord("222");
		user.setCreated(new Date());
		user.setUpdated(new Date());
		userService.createUser(user);
	}
	
	@Test
	public void testLogin(){
		User user = new User();
		user.setUserName("xiaoa");
		user.setPassWord("222");
		userService.checkUser(user);
	}
	
	@Test
	public void testMovieDao(){
//		List<MovieAndMovieInfo> movies = movieDao.getMovies();
//		for (MovieAndMovieInfo movieAndMovieInfo : movies) {
//			System.out.println(movieAndMovieInfo.getMovie().getMovieName());
//		}
	}
	
//	@Test
//	public void testFastDfs(){
//		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/*.xml");
//		try {
//			fastDFSClient.uploadFile(FileUtils.readFileToByteArray(new File("F:/图片识别/1.jpg")));
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	
	@Test
	public void sendMail() throws Exception{
		User user = new User();
		user.setEmail("632443784@qq.com");
		userService.sendMail(user,"s");
	}
	
	/*@Test
	public void movieDaoTest(){
		List<SingleMovie> movies = singeMovieDao.getMovies(2, 10);
		movies.forEach((m) -> System.out.println(m.getMovieName()));
		System.out.println(movies.size());
	}*/
	
	@Test
	public void testJedis(){
		jedisPool.getResource().set("c", "a");
		jedisPool.getResource().get("axxx");
	}
	
	@Test
	public void testNum(){
		System.out.println(singleMovieDao.getCount());
	}
	
	@Test
	public void testMovie(){
		Movie movie = movieService.getMovieAndMovieInfo(2815);
		System.out.println(movie.getMovieInfo() == null);
		System.out.println(movie.getMovieInfo().getStarring());
		System.out.println(movie.toString());
		System.out.println(movie == null);
	}
	
/*	@Test
	public void testSingleMovie(){
		int page = 1;
		int num = 10;
		FilterTag filterTag = new FilterTag();
		filterTag.setScore("0-9");
		filterTag.setArea("全部");
		filterTag.setType("全部");
		filterTag.setYear("全部");
		List<SingleMovie> movies = singleMovieDao.getMovies(page, num, filterTag);
		System.out.println(movies.size());
		movies.forEach((m) -> System.out.println(m.toString()));
	}*/
	
	@Test
	public void testSearch(){
		NObject nObject = movieDao.getMovies(1, 11, "肖申克的救赎");
		List<Movie> movies = (List<Movie>) nObject.getObjects();
		
		for (Movie movie : movies) {
			System.err.println(movie.toString());
		}
		
	}
	
	@Test
	public void testComments(){
		NObject nObject = commentsDao.getComments(1, 10, 14687);
		List<Comments> comment = (List<Comments>) nObject.getObjects();
		System.out.println(comment);
		List<ReplyInner> replyInners = new ArrayList<ReplyInner>();
		for (Comments comments : comment) {
			ReplyInner replyInner = new ReplyInner();
			replyInner.setDate(comments.getSendDate());
			replyInner.setMessage(comments.getMessage());
			replyInner.setName(comments.getUser().getUserName());
			replyInners.add(replyInner);
		}
		Reply reply = new Reply();
		reply.setNum(nObject.getNum());
		reply.setReplyInners(replyInners);
		String json = JsonUtils.objectToJson(reply);
		System.out.println(json);
		System.out.println(nObject.getNum());
		System.out.println(comment.get(0).getUser().getUserName());
	}
	
	@Test
	public void sendMessage(){
		
		commentsDao.saveComments(4, 14687, "aaaaaaaa");
	}
	
	@Test
	public void testMovieFlag(){
		
		MovieFlag movieFlag = movieFlagDao.get(4, 14685);
		
		System.out.println(movieFlag.toString());
		
	}
	
	@Test
	public void test(){
		movieFlagDao.save(9, 14685, new MovieFlag());
//		movieFlagDao.save(9, 14685);
	}
	
	@Test
	public void testNum1(){
		int i = movieFlagDao.getLikeSee(14685);
		System.out.println(i);
	}
	
	@Test
	public void testDouban(){
		
		List<DouBan> douBan = douBanService.getDouBan(1);
		for (DouBan douBan2 : douBan) {
			System.out.println(douBan2.toString());
		}
	}
	
	@Test
	public void testLikeMovies(){
		NObject nObject = movieService.getLikeMovies(4, 1, 8);
		List<SingleMovie> movies = (List<SingleMovie>) nObject.getObjects();
		for (SingleMovie movie : movies) {
			System.out.println(movie.toString());
		}
		System.out.println(nObject.getNum());
	}
	
	@Test
	public void testFlag(){
		
		movieFlagDao.get(4, 14707);
	}
	
	@Test
	public void testValue(){
		FilterTag filterTag = new FilterTag();
		filterTag.setArea("全部");
		filterTag.setScore("0-9");
		filterTag.setType("全部");
		filterTag.setYear("全部");
		NObject nObject = singleMovieDao.getMovies(1, 10, filterTag);
		System.out.println(((SingleMovie)(nObject.getObjects().get(0))).getArea());
	}
	
	@Test
	public void testWonderfulmovie(){
		List<Movie> wonderfulMovies = singleMovieDao.getWonderfulMovies("动作 / 奇幻 / 冒险");
		for (Movie singleMovie : wonderfulMovies) {
			System.out.println(singleMovie.toString());
		}
	}
	
	@Test
	public void testRecentlyMovie(){
		Map<Integer, String> movie = singleMovieDao.getRecentlyMovie();
		Set<Entry<Integer,String>> entrySet = movie.entrySet();
		for (Entry<Integer, String> entry : entrySet) {
			System.out.println(entry.getKey() + " "+entry.getValue());
		}
	}
	
	@Test
	public void test4(){
		userDao.getUserTouXiangById("218");
	}
	
	@Test
	public void testFocusUser(){
		List<FocusUser> focusUser = focusUserDao.getFocusUser(220);
		for (FocusUser focusUser2 : focusUser) {
			System.out.println(focusUser2.toString());
		}
	}
	
	@Test
	public void testRemove(){
		focusUserDao.insertFocusUser(220, 218);
		focusUserDao.insertFocusUser(220, 218);
		focusUserDao.insertFocusUser(220, 218);
		focusUserDao.insertFocusUser(220, 218);
	}
	
	@Test
	public void checkUserName(){
		boolean userName = userDao.checkUserName("s");
		System.out.println(userName);
	}
	
	@Test
	public void testMovieFlagSave(){
		MovieFlag movieFlag = new MovieFlag();
		movieFlag.setLikeSee((short)1);
		movieFlag.setWantSee((short)0);
		movieFlag.setSeeBefore((short)0);
		movieFlagDao.save(218, 25, movieFlag);
	}
	
	@Test
	public void getMovieFlag(){
		movieFlagDao.get(218, 48);
	}
	
	@Test
	public void testPrivateMessage(){
		List<User> recentlyUser = privateMessageDao.getRecentlyUser(1);
		System.out.println(recentlyUser.get(0).toString());
	}
	
	@Test
	public void testPrivateMessage2(){
		List<PrivateMessage> privateMessage = privateMessageDao.getPrivateMessage(1, 8);
		privateMessage.forEach((a) -> System.out.println(a.toString()));
	}
	
	@Test
	public void getWonderfulMovie(){
		String type = " ".trim();
		List<Movie> wonderfulMovies = singleMovieService.getWonderfulMovies(type);
		wonderfulMovies.forEach((a) -> System.out.println(a));
		//		singleMovieDao.getWonderfulMovies(" like '%动作%' or m.type like '%动作%' ");
	}
}

