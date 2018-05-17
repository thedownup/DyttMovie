package com.movie.wangye;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.movie.pojo.Link;
import com.movie.pojo.Movie;
import com.movie.pojo.MovieInfo;
import com.movie.until.JsonUtils;

/**
 * @author zjt
 * @Description: 九七电影的爬取实现
 */
@Component
@Scope("prototype")
public class JiuqiOrigin{

	private static final Logger logger = Logger.getLogger(JiuqiOrigin.class);

	public void parseMovie(Document document,Movie movie,SessionFactory sessionFactory
			,CloseableHttpClient httpClient,String url) throws ParseException, ClientProtocolException, IOException {
		logger.debug("~~~~~~~~~~~~~~~~~~~分界线上~~~~~~~~~~~~~~~~~~~~~~~~~~~");

		Session session = sessionFactory.openSession();

		//创建一个电影详细表单
		MovieInfo movieInfo = new MovieInfo();
		//获取详细表格
		Elements span2 = document.getElementsByClass("span2");
		//遍历详情表
		for (Element element : span2) {
			Element infoLabel = element.getElementsByClass("info-label").get(0);
			String info = infoLabel.text();
			Element temp = infoLabel.parent().parent().getElementsByTag("td").get(1);
			StringBuffer content = new StringBuffer();
			//判断是否有A标签
			if (temp.childNodes().size() > 1) {
				Elements tagA = temp.getElementsByTag("a");
				for (Element eachA : tagA) {
					if (eachA.text().indexOf("显示全部") == -1) {
						content.append(eachA.text()+" ");
					}
				}
			}else {
				content = content.append(temp.text());
			}
			//设置movieinfo的属性
			setMovieInfo(content, info, movieInfo,movie);

			logger.debug(info+"~~~~~~~~~~~~~"+content);
		}

		//影片的介绍		
		String introduce = document.getElementsByClass("movie-introduce").get(0).select("p").text();
		//设置介绍
		movieInfo.setMovieIntroduce(introduce);
		logger.debug(introduce);

		movieInfo.setMovie(movie);

		//链接部分-------------------------------------
		String id = url.substring(url.lastIndexOf("/")+1,url.lastIndexOf("."));
		HttpGet httpGet = new HttpGet("http://www.id97.com/videos/resList/"+id);
		Document document1 = Jsoup.parse(EntityUtils.toString(httpClient.execute(httpGet).getEntity()));
		parseMovieInfo(document1, session, movie, movieInfo);
		logger.debug("~~~~~~~~~~~~~~~~~~~分界线下~~~~~~~~~~~~~~~~~~~~~~~~~~~");
	}


	/**
	 * 解析电影链接
	 * @param document
	 * @param session
	 * @param movie
	 * @param movieInfo
	 */
	public void parseMovieInfo(Document document,Session session,Movie movie,MovieInfo movieInfo){
		List<Link> list = new ArrayList<Link>();
		Element xulie = document.getElementById("movieTabContent");
		Element chaoqing = document.getElementById("sourceDown");
		if (xulie != null) {
			Elements tr = xulie.getElementsByTag("tr");
			for (Element td : tr) {
				Elements tag = td.getElementsByTag("td").get(1).getElementsByTag("a");
				String linkname = tag.attr("title");
				String href = tag.attr("href");
				Link link = new Link();
				link.setLinkName(linkname);
				link.setLinkUrl(href);
				list.add(link);
				logger.info(linkname +"!!!!!!!!!!!!!!!!!!!!!!"+href);
			}
		}

		if (chaoqing != null) {
			Elements tr = chaoqing.getElementsByTag("tr");
			for (Element td : tr) {
				Elements tag = td.getElementsByTag("td").get(1).getElementsByTag("a");
				String linkname = tag.attr("title");
				String href = tag.attr("href");
				Link link = new Link();
				link.setLinkName(linkname);
				link.setLinkUrl(href);
				list.add(link);
				logger.info(linkname +"--------------------------"+href);
			}
		}
		String toJson = JsonUtils.objectToJson(list);
		movieInfo.setLink(toJson);
		//级联自动保存movie
		logger.info("级联自动保存movie");
		session.save(movie);
		session.save(movieInfo);
		logger.info(movie.toString());
		session.close();
	}


	private void setMovieInfo(StringBuffer content,String info,MovieInfo movieInfo,Movie movie){
		String type = content.toString();
		switch (info) {
		case "导演":
			movieInfo.setDirector(type);
			break;
		case "编剧":
			movieInfo.setWriters(type);
			break;
		case "主演":
			movieInfo.setStarring(type);
			break;
		case "类型":
			movieInfo.setType(type);
			movie.setType(type);
			break;
		case "地区":
			movie.setArea(type);
			break;
		case "语言":
			movieInfo.setLanguage(type);
			break;
		case "上映时间":
			movie.setYear(type);
			break;
		case "片长":
			movieInfo.setrTime(type);
			break;
		case "又名":
			movieInfo.setAlias(type);
			break;
		case "评分":
			String score = "";
			if (StringUtils.contains(type, "I")) {
				score = type.substring(type.indexOf("瓣")+1,type.indexOf("I")).trim();
			}else {
				score = type.substring(type.indexOf("瓣")+1,type.length()).trim();
			}
			movie.setScore(score);
			break;
		default:
			logger.error("出现未知属性"+type);
			break;
		}
	}
	
	public int getPage(){
		try {
			String url = "http://www.id97.com/movie/";
			Document document = Jsoup.parse(new URL(url).openStream(),"utf-8",url);
			Element element = document.getElementsByClass("pager-bg").get(0).getElementsByTag("li").last();
			int page = Integer.valueOf(element.getElementsByTag("a").get(0).attr("data-ci-pagination-page"));
			return page;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

}
