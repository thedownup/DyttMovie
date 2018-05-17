package com.movie.ipfilter;

import java.io.IOException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.http.HttpHost;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.movie.http.HttpManager;
import com.movie.pojo.IPMessage;

/**
 * @author zjt
 * @Description: 测试ip
 */
@Component
public class IpFilter {

	private Logger logger = Logger.getLogger(IpFilter.class);
	
	@Autowired
	private HttpManager httpManager;
	
	/**
	 * 判断此代理IP是否稳固
	 * @param ipMessage
	 * @return
	 */
	public boolean isTable(IPMessage ipMessage){
		CloseableHttpClient httpClient = null;
		CloseableHttpResponse response = null;
		String url = "http://my.163.com/";
		Lock lock = null;
			try {
				lock = new ReentrantLock();
				lock.lock();
				httpClient = httpManager.createHttpClient(5000,new HttpHost(ipMessage.getIpAdress(), ipMessage.getPort()));
				try {
					response = httpClient.execute(new HttpGet(url));
				} catch (IOException e) {
					logger.error(e.getLocalizedMessage());
					return false;
				}
			} finally {
				lock.unlock();
			}
			if (response == null || response.equals("")) {
				return false;
			}
			if (response.getStatusLine().getStatusCode() == 200) {
				return true;
			}else {
				return false;
			}

	}
}
