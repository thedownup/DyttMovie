package com.movie.thread;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Callable;

import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.movie.http.HttpManager;
import com.movie.pojo.Movie;
import com.movie.wangye.BaseDyttOrigin;

public class DyttFirstOriginThread extends BaseDyttOrigin implements Callable<Void>{

	private static final Logger logger = Logger.getLogger(DyttFirstOriginThread.class);

	private Element table;
	private HttpManager httpManager;
	private SessionFactory sessionFactory;

	public DyttFirstOriginThread( SessionFactory sessionFactory,HttpManager httpManager,Element table){
		this.table = table;
		this.httpManager = httpManager;
		this.sessionFactory = sessionFactory;
	}


	public void parseTable() throws ParseException, IOException{
		Elements trs = table.getElementsByTag("tr");
		logger.info(trs.text());
		//电影名字
		Movie movie = new Movie();
		String moviename = trs.get(1).getElementsByTag("a").get(0).text();
		movie.setMovieName(moviename);
		String clearity = getMovieClarity(moviename);
		//电影清晰度
		movie.setClarity(clearity);
		String url = baseUrl+trs.get(1).getElementsByTag("a").get(0).attr("href");
		logger.info("本电影网址"+url+"----------------------------");
		//创建时间
		movie.setDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

		CloseableHttpResponse response = httpManager.getContextResponse(new HttpGet(url));
		String html = EntityUtils.toString((response.getEntity()), "GBK");
		Document document2 = Jsoup.parse(html);
		Session session = sessionFactory.openSession();
		parseMovieInfo(document2,session,movie);
	}

	@Override
	public Void call() throws Exception {
		parseTable();
		return null;
	}

}
