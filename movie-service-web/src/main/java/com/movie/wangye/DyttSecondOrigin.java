package com.movie.wangye;

import java.io.IOException;
import java.util.concurrent.ExecutorService;

import org.apache.http.ParseException;
import org.hibernate.SessionFactory;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.movie.http.HttpManager;
import com.movie.thread.DyttSecondOriginThread;

/**
 * @author zjt
 * @Description: 获取电影天堂 第二种网页布局版本的实现
 */
@Component
@Scope("prototype")
public class DyttSecondOrigin{

	public void parseMovie(Document document, SessionFactory sessionFactory,ExecutorService pool,HttpManager httpManager) throws ParseException, IOException {
		//获取到每一列的电影简介
		Elements tables = document.getElementsByClass("tbspan");
		for (Element table : tables) {
			pool.submit(new DyttSecondOriginThread(sessionFactory, httpManager,table));
		}
	}


}
