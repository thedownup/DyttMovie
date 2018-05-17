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

public class DyttSecondOriginThread extends BaseDyttOrigin implements Callable<Void>{

	private static final Logger logger = Logger.getLogger(DyttSecondOriginThread.class);

	private Element table;
	private HttpManager httpManager;
	private SessionFactory sessionFactory;

	public DyttSecondOriginThread( SessionFactory sessionFactory,HttpManager httpManager,Element table){
		this.table = table;
		this.httpManager = httpManager;
		this.sessionFactory = sessionFactory;
	}


	public void parseTable() throws ParseException, IOException{
		Session session = sessionFactory.openSession();
		//获取电影的url
		String afurl = table.getElementsByTag("tr").get(1).getElementsByTag("td").get(1)
				.getElementsByTag("a").get(1).attr("href");
		String url = baseUrl + afurl;
		logger.info("本电影网址"+url+"----------------------------");
		CloseableHttpResponse response = httpManager.getContextResponse(new HttpGet(url));
		Document document2 = Jsoup.parse(EntityUtils.toString(response.getEntity(),"GBK"));
		Movie movie = new Movie();
		Elements trs = table.getElementsByTag("tr");
		String moviename = trs.get(1).getElementsByTag("a").get(1).text();
		//更新获取的时间
		movie.setDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		String clearity = getMovieClarity(moviename);
		movie.setClarity(clearity);
		logger.debug(url);
		parseMovieInfo(document2, session, movie);
	}


	@Override
	public Void call() throws Exception {
		parseTable();
		return null;
	}

}
