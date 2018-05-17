package com.movie.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.movie.ipfilter.IpFilter;
import com.movie.pojo.IPMessage;
import com.movie.pojo.IpAndFut;
import com.movie.proxyip.VipProxy;
import com.movie.reids.RedisHelper;
import com.movie.service.ProxyIpPoolService;
import com.movie.thread.ProxyIpSupport;


@Service
public class ProxyIpPoolServiceImpl implements ProxyIpPoolService {
	
	private static Logger logger = Logger.getLogger(ProxyIpPoolServiceImpl.class);
	
	/*@Autowired
	private XiCiProxy xiCiProxy;
	@Autowired
	private KuaiProxy kuaiProxy;
	@Autowired
	private FeiLongProxy feiLongProxy;*/
	@Autowired
	private VipProxy vipProxy;
	@Autowired
	private IpFilter ipFilter;
	@Autowired
	private RedisHelper redisHelper;
	private final int XICIPROXYPAGE = 40;

	
	@Override
	public int insertProxyIp() throws Exception{
		
		int count = 0;
		
		//创建线程池
		ExecutorService pool = Executors.newFixedThreadPool(200);
		
		List<IpAndFut> iFuts = new ArrayList<IpAndFut>();
		
		List<IPMessage> ipMessages = vipProxy.getIpMessages(XICIPROXYPAGE);
		for (IPMessage ipMessage : ipMessages) {
			
			ProxyIpSupport proxyIpSupport = new ProxyIpSupport(ipMessage,ipFilter);
			Future<Boolean> future = pool.submit(proxyIpSupport);
			IpAndFut ipAndFut = new IpAndFut();
			ipAndFut.setFuture(future);
			ipAndFut.setIpMessage(ipMessage);
			iFuts.add(ipAndFut);
		}
		
		for (IpAndFut iFut : iFuts) {
			try {
				if (iFut.getFuture().get().booleanValue() == true) {
					redisHelper.insertIpMessageToRedis(iFut.getIpMessage());
					count++;
				}else {
					logger.info("过滤IP"+iFut.getIpMessage().toString());
				}
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
				logger.error(e.getLocalizedMessage());
			}
		}
		pool.shutdown();
		
		return count;
	}
}



