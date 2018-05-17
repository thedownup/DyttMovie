package com.movie.wangye;


import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.movie.http.HttpManager;
import com.movie.pojo.Link;
import com.movie.pojo.Movie;
import com.movie.pojo.MovieInfo;
import com.movie.until.JsonUtils;

/**
 * @author zjt
 * @Description: 爬取电影天堂的基类
 */
@Component
@Scope("prototype")
public class BaseDyttOrigin {

	private static final Logger logger = Logger.getLogger(BaseDyttOrigin.class);

	@Autowired
	protected HttpManager httpManager;
	@Value("${BASEURL}")
	protected String baseUrl = "http://www.ygdy8.net";

	
	//电影天堂两种不同页面选择
	//最新电影
	public static final String NEWMOVIETYPE = "NEWMOVIETYPE";
	public static final String NEWMOVIE = "http://www.ygdy8.net";
	public static final String BASENEWMOVIE = "http://www.ygdy8.net/html/gndy/dyzz/list_23_";
	
	//国内电影
	public static final String CHINAMOVIETYPE = "CHINAMOVIETYPE";
	public static final String CHINAMOVIE = "http://www.ygdy8.net";
	public static final String BASECHINAMOVIE = "http://www.ygdy8.net/html/gndy/china/list_4_";

	//欧美电影
	public static final String OUMEIMOVIETYPE = "OUMEIMOVIETYPE";
	public static final String OUMEIMOVIE = "http://www.ygdy8.net";
	public static final String BASEOUMEIMOVIE = "http://www.ygdy8.net/html/gndy/oumei/list_7_";


	private final String BD = "BD";
	private final String HD = "HD";
	private final String DVD = "DVD";

	/**
	 * 解析每一部电影的详细情况
	 * @param document
	 * @param session
	 * @param movie
	 * @throws Exception
	 */
	public void parseMovieInfo(Document document,Session session,Movie movie) {
		Element zoom = document.getElementById("Zoom");
		String imgUrl = zoom.getElementsByTag("img").get(0).attr("src");
		movie.setMovieImgUrl(imgUrl);
		String director = zoom.getElementsByTag("span").get(0).text();
		String[] each = director.split("◎");
		MovieInfo movieInfo = new MovieInfo();
		setMovieInfoAndMovie(each, movieInfo, movie);
		logger.info("movieInfo -> "+"["+movieInfo.toString()+"]");
		//存入数据库
		Elements tables = zoom.getElementsByTag("table");
		List<Link> links = new ArrayList<Link>();
		setLinks(tables, links);
		movieInfo.setLink(JsonUtils.objectToJson(links));
		movieInfo.setMovie(movie);
		session.save(movieInfo);
		logger.info("自动保存电影 -->  "+movie.getMovieName());
		session.close();
	}


	public void setLinks(Elements tables,List<Link> links){
		for (Element table : tables) {
			String url = table.getElementsByTag("a").attr("href");
			if (StringUtils.contains(url, "ftp")) {
				Link link = new Link();
				link.setLinkName(url);
				link.setLinkUrl(url);
				links.add(link);
			}else {
				Link link = new Link();
				link.setLinkName("磁力链下载点击这里");
				link.setLinkUrl(url);
				links.add(link);
			}
		}
	}


	/**
	 * 解析电影天堂的电影描述
	 * @param each
	 * @param movieInfo
	 * @param movie
	 * @throws Exception
	 */
	public void setMovieInfoAndMovie(String[] each,MovieInfo movieInfo,Movie movie) {

		for (String info : each) {
			//去掉空格
			String type = "";
			String value = "";
			if (info.length() >= 4) {
				type = info.substring(0, 4).replaceAll("　　", "");
				value = info.substring(5);
			} else {
				type = info.replaceAll("　　", "");
				value = info;
			}
			switch (type) {
			case "导演":
				movieInfo.setDirector(value);
				movieInfo.setWriters(value);
				break;
			case "片名":
				movie.setMovieName(value);
				break;
			case "主演":
				movieInfo.setStarring(value);
				break;
			case "类别":
				movieInfo.setType(value);
				movie.setType(value);
				break;
			case "语言":
				movieInfo.setLanguage(value);
				break;
			case "产地":
				movie.setArea(value);
				break;
			case "上映日期":
				movie.setYear(value);
				break;
			case "译名":
				movieInfo.setAlias(value);
				break;
			case "豆瓣评分":
				if (value.indexOf("/") != -1) {
					movie.setScore(value.substring(0,value.indexOf("/")));
				}else {
					movie.setScore(value);
				}
				break;
			case "简介":
				if (StringUtils.contains(value, "【下载地址】"))
					movieInfo.setMovieIntroduce(value.substring(0,value.indexOf("【下载地址】")).trim());
				else 
					movieInfo.setMovieIntroduce(value);
				break;
			case "片长":
				movieInfo.setrTime(value);
				break;
			case "年份":
				movie.setYear(value);
				break;
			default:
				logger.debug("没有匹配的选项"+type);
				break;
			}
		}

	}

	/**
	 * 解析清晰度
	 * @param str
	 * @return
	 */
	public String getMovieClarity(String str){

		if (StringUtils.contains(str, BD)) {
			return "超清";
		}else if (StringUtils.contains(str, HD)) {
			return "高清";
		}else if (StringUtils.contains(str, DVD)) {
			return "DVD";
		}
		return " ";
	}

	public int getPage(String url) {
		int page = 0;
		try {
			Document document = Jsoup.parse(new URL(url).openStream(),"GBK",url);
			Element select = document.getElementsByTag("select").last();
			Elements options = select.getElementsByTag("option");
			page = options.size();
			logger.info(url+"地址有"+options.size()+"条");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return page;
	}

	/**
	 * 选择栏目所对应的样式 错误返回 -1
	 * @return
	 */
	public static int getPageType(String type){
		if (type.equals(NEWMOVIETYPE) ) return 1;
		else if (type.equals(OUMEIMOVIETYPE) || type.equals(CHINAMOVIETYPE)) return 2;
		return -1;
	}
}
