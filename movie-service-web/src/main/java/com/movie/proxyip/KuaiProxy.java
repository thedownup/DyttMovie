package com.movie.proxyip;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.movie.http.HttpManager;
import com.movie.pojo.IPMessage;

/**
 * @author zjt
 * @Description: 快代理的爬虫
 */
@Component
public class KuaiProxy {
	private static Logger logger = Logger.getLogger(KuaiProxy.class);

	@Autowired
	private HttpManager httpManager;

	private final float SPEED = 5;  


	//获取代理ip page为取的页数
	public List<IPMessage> getIpMessages(int page){
		CloseableHttpClient httpClient = httpManager.createHttpClient();
		List<IPMessage> proxyIp = new ArrayList<IPMessage>();
		for (int i = 1; i <= page; i++) {
			try {
				//				CloseableHttpResponse response = httpClient.execute(new HttpGet("https://www.kuaidaili.com/ops/proxylist/"+page));
				//				Document document = Jsoup.parse(EntityUtils.toString(response.getEntity()));
				Document document = Jsoup.connect("https://www.kuaidaili.com/ops/proxylist/"+page)
						.timeout(5000)
						.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
						.header("Accept-Encoding", "gzip, deflate, sdch")
						.header("Accept-Language", "zh-CN,zh;q=0.8")  
						.header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36").get();
				Elements table = document.getElementById("freelist").select(".table-b");
				if (table.isEmpty()) {
					continue;
				}
				Elements tr = table.get(0).getElementsByTag("tbody").first().getElementsByTag("tr");
				for (Element tds : tr) {
					String spd = tds.getElementsByTag("td").get(5).text();
					float speed = Float.parseFloat(spd.substring(0, spd.length()-1));
					if (speed > SPEED) {
						continue;
					}
					IPMessage ipMessage = new IPMessage();
					ipMessage.setIpAdress(tds.getElementsByTag("td").get(0).text());
					ipMessage.setPort(Integer.valueOf(tds.getElementsByTag("td").get(1).text()));
					ipMessage.setType(tds.getElementsByTag("td").get(3).text());
					ipMessage.setSpeed(speed);
					ipMessage.setLife(tds.getElementsByTag("td").get(6).text());
					proxyIp.add(ipMessage);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return proxyIp;
	}
}
