package com.movie.proxyip;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.movie.http.HttpManager;
import com.movie.pojo.IPMessage;

/**
 * @author zjt
 * @Description: 花钱的所以快
 */
@Component
public class VipProxy {
	
	private static final Logger logger = Logger.getLogger(VipProxy.class);

	@Autowired
	private HttpManager httpManager;

	//获取代理ip page为取的页数
	public List<IPMessage> getIpMessages(int num){
		List<IPMessage> proxyIp = new ArrayList<IPMessage>();
		// api  http://www.daxiangdaili.com/api?tid=557211203278238
		String url = "http://tvp.daxiangdaili.com/ip/?tid=557211203278238&num="+num
				+ "&delay=1&category=2&filter=on&format=json&protocol=http";
		CloseableHttpResponse response = httpManager.getResponse(url);
		String json;
		try {
			json = EntityUtils.toString(response.getEntity());
			logger.info(json);
			JsonArray jsonArray = new JsonParser().parse(json).getAsJsonArray();
			for (JsonElement jsonElement : jsonArray) {
				String host = jsonElement.getAsJsonObject().get("host").getAsString();
				String port = jsonElement.getAsJsonObject().get("port").getAsString();
				IPMessage ipMessage = new IPMessage();
				ipMessage.setIpAdress(host);
				ipMessage.setType("http");
				ipMessage.setLife("0");
				ipMessage.setPort(Integer.valueOf(port));
				ipMessage.setSpeed(0);
				proxyIp.add(ipMessage);
				
			}
		} catch (Exception e) {
			logger.error("vip代理发生错误"+e.getMessage());
			e.printStackTrace();
		}
		logger.info("vip代理一共用"+proxyIp.size()+"个");
		return proxyIp;
	}

}
