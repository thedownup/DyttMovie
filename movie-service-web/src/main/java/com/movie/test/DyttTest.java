package com.movie.test;


import org.apache.commons.lang.StringUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.movie.http.HttpManager;
import com.movie.pojo.Link;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/spring/*.xml"})
public class DyttTest {

	@Autowired
	private HttpManager httpManager;
	
	@Test
	public void testDytt(){
		CloseableHttpResponse response = httpManager.getContextResponse(new HttpGet("http://www.ygdy8.net/html/gndy/jddy/20130313/41670.html"));
		try {
			Document document = Jsoup.parse(EntityUtils.toString(response.getEntity(),"GBK"));
//			Document document = Jsoup.parse("http://www.ygdy8.net/html/gndy/jddy/20130313/41670.html");
			
			Element zoom = document.getElementById("Zoom");
			String imgUrl = zoom.getElementsByTag("img").get(0).attr("src");
			Elements tables = zoom.getElementsByTag("table");
			for (Element table : tables) {
				String url = table.getElementsByTag("a").attr("href");
				if (StringUtils.contains(url, "ftp")) {
					Link link = new Link();
					link.setLinkName(url);
					link.setLinkUrl(url);
				}else {
					Link link = new Link();
					link.setLinkName("磁力链下载点击这里");
					link.setLinkUrl(url);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void two(){
		CloseableHttpResponse response = httpManager.getContextResponse(new HttpGet("http://www.ygdy8.net/html/gndy/dyzz/20180416/56694.html"));
		try {
			Document document = Jsoup.parse(EntityUtils.toString(response.getEntity(),"GBK"));
//			Document document = Jsoup.parse("http://www.ygdy8.net/html/gndy/jddy/20130313/41670.html");
			
			Element zoom = document.getElementById("Zoom");
			String imgUrl = zoom.getElementsByTag("img").get(0).attr("src");
			System.out.println(imgUrl+"---------------");
			String director = zoom.getElementsByTag("span").get(0).text();
			String[] each = director.split("◎");
			setMovieInfoAndMovie(each);
			Elements tables = zoom.getElementsByTag("table");
			System.out.println(tables.size());
			for (Element table : tables) {
				String url = table.getElementsByTag("a").attr("href");
				if (StringUtils.contains(url, "ftp")) {
					Link link = new Link();
					link.setLinkName(url);
					link.setLinkUrl(url);
				}else {
					Link link = new Link();
					link.setLinkName("磁力链下载点击这里");
					link.setLinkUrl(url);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void setMovieInfoAndMovie(String[] each) {

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
		/*	String value = info.substring(5);*/
			switch (type) {
			case "导演":
				System.out.println(type);
				break;
			case "片名":
				System.out.println(type);
				break;
			case "主演":
				System.out.println(type);
				break;
			case "类别":
				System.out.println(type);
				break;
			case "语言":
				System.out.println(type);
				break;
			case "产地":
				System.out.println(type);
				break;
			case "上映日期":
				System.out.println(type);
				break;
			case "译名":
				System.out.println(type);
				break;
			case "豆瓣评分":
				System.out.println(type);
				break;
			case "简介":
				System.out.println(type);
				break;
			case "片长":
				System.out.println(type);
				break;
			default:
				System.out.println(type);
				break;
			}
		}

	}
	
}
