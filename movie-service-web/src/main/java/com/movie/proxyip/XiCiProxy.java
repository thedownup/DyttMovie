package com.movie.proxyip;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import com.movie.pojo.IPMessage;

/**
 * @author zjt
 * @Description: 对XiciDaili代理的一些常用操作
 */
@Component
public class XiCiProxy {
	
	private final float SPEED = 5;  
	
	private static final Logger logger = Logger.getLogger(XiCiProxy.class);

	//获取代理ip page为取的页数
	public List<IPMessage> getIpMessages(int page){
		List<IPMessage> proxyIp = new ArrayList<IPMessage>();
		for (int i = 1; i <= page; i++) {
			try {
				Document document = Jsoup.connect("http://www.xicidaili.com/nn/"+page)
						.timeout(5000)
						.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
						.header("Accept-Encoding", "gzip, deflate, sdch")
						.header("Accept-Language", "zh-CN,zh;q=0.8")  
						.header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36").get();
				Element table = document.getElementById("ip_list");
				Elements tr = table.getElementsByClass("odd");
				for (Element element : tr) {
					String ip = element.getElementsByTag("td").get(1).text();
					String port = element.getElementsByTag("td").get(2).text();
					String type = element.getElementsByTag("td").get(5).text();
					String tempSpeed = element.getElementsByTag("td").get(6).getElementsByTag("div").get(0).attr("title");
					//去掉秒 
					float speed = Float.parseFloat(tempSpeed.substring(0,tempSpeed.length()-1));
					String life = element.getElementsByTag("td").get(8).text();
					if (!StringUtils.contains(life, "分钟") && (speed < SPEED) ) {
						IPMessage ipMessage = new IPMessage();
						ipMessage.setIpAdress(ip);
						ipMessage.setType(type);
						ipMessage.setLife(life);
						ipMessage.setPort(Integer.valueOf(port));
						ipMessage.setSpeed(speed);
						proxyIp.add(ipMessage);
					}
				}
				Thread.sleep(20000);
			} catch (IOException | InterruptedException e) {
				logger.error("连接西刺发生错误"+e.getMessage());
			}
		}
		logger.info("西刺代理一共有"+proxyIp.size()+"个代理ip可以进行筛选");
		return proxyIp;
	}
	
}
