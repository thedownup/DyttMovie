package com.movie.task;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.movie.reids.RedisHelper;
import com.movie.service.ProxyIpPoolService;

/**
 * 
 * @author zjt
 * @Description: 维护redis的ip代理池
 */
@Component
public class ProxyIpTask {

	private static final Logger logger = Logger.getLogger(ProxyIpTask.class);
	
	@Autowired
	private RedisHelper redisHelper;
	@Autowired
	private ProxyIpPoolService proxyIpPoolService;


	/**
	 * 插入新的代理ip 每两小时一次
	 */
	@Scheduled(cron="0 0 0/2 1/4 * ? ")
	public void updateProxyIp(){
		
		logger.info("定时更新代理池");
		
		//小于设定的值
		if (redisHelper.getIpMessageNum() < 20) {
			try {
				proxyIpPoolService.insertProxyIp();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 定期检测ip可用ip 每一小时一次
	 */
	@Scheduled(cron="0 0 0/1 1/4 * ? ")
	public void checkProxyIp(){
		
		logger.info("定时更新检查代理池");
		//检测现有ip池ip
		redisHelper.checkProxyIp();
		//维持一定量的ip数量		
		updateProxyIp();
	}
	
	
}
