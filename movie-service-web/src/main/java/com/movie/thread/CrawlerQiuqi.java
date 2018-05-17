package com.movie.thread;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.hibernate.SessionFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.movie.pojo.Movie;
import com.movie.wangye.JiuqiOrigin;

public class CrawlerQiuqi implements Runnable{
	
	
	private String url;
	private Movie movie;
	private JiuqiOrigin jiuqiOrigin;
	private SessionFactory sessionFactory;
	private CloseableHttpClient httpClient;

	public CrawlerQiuqi(String url,CloseableHttpClient httpClient,SessionFactory sessionFactory,Movie movie,JiuqiOrigin jiuqiOrigin){
		this.url = url;
		this.movie = movie;
		this.jiuqiOrigin = jiuqiOrigin;
		this.httpClient = httpClient;
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void run() {
		HttpGet httpGet = new HttpGet(url);
		try {
			CloseableHttpResponse response = httpClient.execute(httpGet);
			String content = EntityUtils.toString(response.getEntity());
			Document document = Jsoup.parse(content);
			jiuqiOrigin.parseMovie(document, movie, sessionFactory, httpClient, url);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
}
