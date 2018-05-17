package com.movie.interfaces;

import java.io.IOException;

import org.apache.http.ParseException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.jsoup.nodes.Document;

import com.movie.pojo.Movie;

/**
 * @author zjt
 * @Description: 这是一个电影来源接口
 */
public interface MovieOrigin {
	
	public void parseMovie(Document document,SessionFactory sessionFactory) throws ParseException, IOException;
	
	public void parseMovieInfo(Document document,Session session,Movie movie);
	
	
}
