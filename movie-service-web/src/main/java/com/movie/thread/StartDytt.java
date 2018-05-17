package com.movie.thread;

import java.io.IOException;
import java.util.concurrent.ExecutorService;

import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.movie.http.HttpManager;
import com.movie.wangye.BaseDyttOrigin;
import com.movie.wangye.DyttFirstOrigin;
import com.movie.wangye.DyttSecondOrigin;

/**
 * @author zjt
 * @Description: 用来开始爬取每一页的
 */
public class StartDytt {

	private Logger logger = Logger.getLogger(StartDytt.class);

	private String url;
	private String type;
	private HttpManager httpManager;
	private SessionFactory sessionFactory;
	private ExecutorService pool;

	public StartDytt(String type,String url,HttpManager httpManager,SessionFactory sessionFactory,ExecutorService pool){
		this.url = url;
		this.type = type;
		this.httpManager = httpManager;
		this.sessionFactory = sessionFactory;
		this.pool = pool;
	}

	public void start() throws ParseException, IOException {
		HttpGet httpGet = new HttpGet(url);
		CloseableHttpResponse response = httpManager.getContextResponse(httpGet);
		String content = null;
		content = EntityUtils.toString(response.getEntity(),"GBK");
		Document document = Jsoup.parse(content);
		//解析每一页
		if (BaseDyttOrigin.getPageType(type) == 1) {
			new DyttFirstOrigin().parseMovie(document, sessionFactory,pool,httpManager);
		}else if (BaseDyttOrigin.getPageType(type) == 2) {
			new DyttSecondOrigin().parseMovie(document, sessionFactory,pool,httpManager);
		}else {
			logger.error("解析类型错误请选择正确的格式");
		}
	}
}
