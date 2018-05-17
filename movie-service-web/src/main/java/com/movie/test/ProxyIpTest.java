package com.movie.test;

import org.apache.http.HttpHost;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.movie.http.HttpManager;
import com.movie.pojo.IPMessage;
import com.movie.proxyip.VipProxy;
import com.movie.reids.RedisHelper;
import com.movie.service.DbService;
import com.movie.service.ProxyIpPoolService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/spring/*.xml"})
public class ProxyIpTest {
	
	@Autowired
	private DbService dbService;
	@Autowired
	private VipProxy vipProxy;
	@Autowired
	private HttpManager HttpManager;
	@Autowired
	private RedisHelper redisHelper;
	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private ProxyIpPoolService proxyIpPoolService;
	
	@Test
	public void testIsTable(){
		HttpHost proxy = new HttpHost("121.231.168.149",6666,"http");
		CloseableHttpResponse response = HttpManager.getResponse("https://www.baidu.com/", proxy);
		System.out.println(response.getStatusLine());
		
	}
	
	@Test
	public void saveDataToDb(){
		sessionFactory.openSession();
		dbService.save97MovieToDb();
	}
	
	@Test
	public void testRedis(){
		redisHelper.insertIpMessageToRedis(new IPMessage());
	}
	
	@Test
	public void testJson(){
		vipProxy.getIpMessages(2);
	}
	
	/*@Test
	public void insertIntoProxyIp(){
		proxyIpPoolService.insertProxyIp();
	}*/

	/*@Test
	public void testRedisProxyIpNum(){
		int num = redisHelper.checkProxyIp();
		System.out.println(num);
	}*/
	
}
