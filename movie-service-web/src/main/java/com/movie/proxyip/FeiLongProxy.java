package com.movie.proxyip;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import com.movie.pojo.IPMessage;

/**
 * @author zjt
 * @Description: 飞龙的代理ip http://www.feilongip.com/
 */
@Component
public class FeiLongProxy {

	private Logger logger = Logger.getLogger(FeiLongProxy.class);
	//获取代理ip page为取的页数
	public List<IPMessage> getIpMessages(int page){
		List<IPMessage> proxyIp = new ArrayList<IPMessage>();
		try {
			Document document = Jsoup.connect("http://www.feilongip.com/")
					.timeout(5000)
					.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
					.header("Accept-Encoding", "gzip, deflate, sdch")
					.header("Accept-Language", "zh-CN,zh;q=0.8")  
					.header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36").get();

			Element tbody = document.getElementsByClass("FreeIptbody").get(0);
			Elements tr = tbody.getElementsByTag("tr");
			for (Element tds : tr) {
				IPMessage ipMessage = new IPMessage();
				String ipAndPort = tds.getElementsByTag("td").get(1).text();
				String ip = ipAndPort.substring(0,ipAndPort.indexOf(":"));
				int port = Integer.valueOf(ipAndPort.substring(ipAndPort.indexOf(":")+1));

				ipMessage.setIpAdress(ip);
				ipMessage.setPort(port);
				ipMessage.setType(tds.getElementsByTag("td").get(4).getElementsByTag("a").get(0).text());
				//没有速度默认
				ipMessage.setSpeed(1);
				ipMessage.setLife("");
				logger.info(ipMessage.toString());
				proxyIp.add(ipMessage);
			}

			Thread.sleep(20000);
		} catch (IOException | InterruptedException e) {
			logger.error("飞龙代理发生错误"+e.getMessage());
		}
		logger.info("飞龙代理一共有"+proxyIp.size()+"个代理ip可以进行筛选");
		return proxyIp;
	}
}
