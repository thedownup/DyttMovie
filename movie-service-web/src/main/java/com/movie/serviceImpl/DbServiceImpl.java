package com.movie.serviceImpl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang.StringUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.movie.dao.PageFlagDao;
import com.movie.http.HttpManager;
import com.movie.pojo.Movie;
import com.movie.service.DbService;
import com.movie.thread.CrawlerQiuqi;
import com.movie.thread.StartDytt;
import com.movie.wangye.JiuqiOrigin;


@Service
public class DbServiceImpl implements DbService {

	private static final Logger logger = Logger.getLogger(DbServiceImpl.class);

	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private HttpManager httpManager;
	@Autowired
	private JiuqiOrigin jiuqiOrigin;
	@Autowired
	private PageFlagDao pageFlagDao;

	private final int jiuQiPageNum = 362;
	private final int poolSize = 100;

	@Override
	public void save97MovieToDb(){
		//初始化一个线程池
		ExecutorService pool = Executors.newFixedThreadPool(poolSize);
		//先从创建默认的 以后再改
		CloseableHttpClient httpClient = httpManager.createContextClient();
		//从redis中获取ipmessage 
		int page = 1;
		for (int i = page; i <= jiuQiPageNum; i++) {
			logger.debug("现在开始爬取第"+i);                         
			logger.info("当前是"+"http://www.id97.com/movie?"+"page="+i);
			try {
				HttpGet httpGet = new HttpGet("http://www.id97.com/movie?"+"page="+i);
				CloseableHttpResponse response = httpClient.execute(httpGet);
				Document document = Jsoup.parse(EntityUtils.toString(response.getEntity()));
				Elements container = document.getElementsByClass("movie-item-in");
				for (Element element : container) {
					String title = element.getElementsByTag("a").attr("title");
					String href = element.getElementsByTag("a").select("img").get(0).attr("data-original");
					String clarity = element.getElementsByClass("qtag").text();
					//构建对象保存进数据库
					Movie movie = new Movie();
					movie.setMovieName(title);
					movie.setMovieImgUrl(href);
					movie.setClarity(clarity);
					Date date = new Date();
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					movie.setDate(simpleDateFormat.format(date));

					String url = element.getElementsByTag("a").attr("href");
					if (StringUtils.isBlank(title)) {
						logger.error("爬取ip遭到屏蔽");
					}else {
						pool.submit(new CrawlerQiuqi(url, httpClient, sessionFactory, movie,jiuqiOrigin));
					}
					//一页爬完休息数据
				}
				Thread.sleep(5_000);
			} catch (Exception e) {
				logger.error("save97MovieToDb发生未知错误"+e.getLocalizedMessage());
			}
		}
	}
	
	
	@Override
	public void update97MovieToDb(int beg,int end){
		//初始化一个线程池
		ExecutorService pool = Executors.newFixedThreadPool(poolSize);
		//先从创建默认的 以后再改
		CloseableHttpClient httpClient = httpManager.createContextClient();
		//从redis中获取ipmessage 
		int page = 1;
		for (int i = beg; i <= end; i++) {
			try {
				HttpGet httpGet = new HttpGet("http://www.id97.com/movie?"+"page="+i);
				CloseableHttpResponse response = httpClient.execute(httpGet);
				Document document = Jsoup.parse(EntityUtils.toString(response.getEntity()));
				Elements container = document.getElementsByClass("movie-item-in");
				for (Element element : container) {
					String title = element.getElementsByTag("a").attr("title");
					String href = element.getElementsByTag("a").select("img").get(0).attr("data-original");
					String clarity = element.getElementsByClass("qtag").text();
					//构建对象保存进数据库
					Movie movie = new Movie();
					movie.setMovieName(title);
					movie.setMovieImgUrl(href);
					movie.setClarity(clarity);
					Date date = new Date();
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					movie.setDate(simpleDateFormat.format(date));

					logger.debug(title+"  现在在第"+i);                         
					String url = element.getElementsByTag("a").attr("href");
					logger.info("当前是"+"http://www.id97.com/movie?"+"page="+i);
					if (StringUtils.isBlank(title)) {
						logger.error("爬取ip遭到屏蔽");
					}else {
						pool.submit(new CrawlerQiuqi(url, httpClient, sessionFactory, movie,jiuqiOrigin));
					}
					//一页爬完休息数据
				}
				Thread.sleep(5_000);
			} catch (Exception e) {
				logger.error("save97MovieToDb发生未知错误"+e.getLocalizedMessage());
			}
		}
	}

	@Override
	public void saveDyttMovieToDb(String type,int beg,int end,String begUrl){
		//创建线程池
		ExecutorService pool = Executors.newFixedThreadPool(poolSize);
		for (int i = beg; i <= end; i++) {
			try {
				String url = begUrl+i+".html";
				new StartDytt(type,url, httpManager, sessionFactory, pool).start();
				logger.info("~~~~~~~~~~~~~~~~~^^^^^^^^^^^^^^^^^~~~~~~~~~~~~~~~~");
				logger.info("~~~~~~~~~~~~~~~~"+"本页爬取完毕"+url+"~~~~~~~~~~~~~~~~");
				logger.info("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
				Thread.sleep(5_000);
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}
	}
	

	/**
	 * 更新
	 */
	@Override
	public int updateMovieFromDytt(String type,int endPage,String begUrl){
		//查询数据库看上次查到的记录
		int page = pageFlagDao.getLastPage(type,begUrl+"1");
		//有多余页数出现说明有更新
		if (endPage > page) {
			logger.info("检查到有更新" + begUrl);
			saveDyttMovieToDb(type, 1, endPage-page, begUrl);
			pageFlagDao.setPage(type, endPage);
			return endPage - page;
		} else {
			logger.info("检查到并没有更新" + begUrl);
			return 0; 
		}
	}

}	

