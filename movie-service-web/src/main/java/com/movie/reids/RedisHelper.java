package com.movie.reids;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.movie.ipfilter.IpFilter;
import com.movie.pojo.IPMessage;
import com.movie.until.JsonUtils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author zjt
 * @Description: redis的常用操作
 */
@Component
public class RedisHelper {

	private static final Logger logger = Logger.getLogger(RedisHelper .class);

	@Autowired
	private IpFilter ipFilter;
	@Autowired
	private JedisPool jedisPool;

	private Jedis jedis;

	private static final String IPMESSAGE = "IPMESSAGE";

	private static final String ROLLINGNAME = "Rolling";
	private static final String ROLLINGID = "rid";

	private final int MAXNUM = 20;
	private final long CONNECTTIMEOUT = 10000;

	private synchronized Jedis getJedis(){
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		jedisPoolConfig.setMaxTotal(MAXNUM);
		jedisPoolConfig.setMaxWaitMillis(CONNECTTIMEOUT);
		return jedisPool.getResource();
	}

	/**
	 * 初始化jedis
	 */
	@PostConstruct
	public void init(){
		jedis = getJedis();
	}

	/**
	 * 插入可以用的代理ip
	 * @param ipMessage
	 */
	public void insertIpMessageToRedis(IPMessage ipMessage){
		//从池中获取redis
		jedis.sadd(IPMESSAGE, JsonUtils.objectToJson(ipMessage),JsonUtils.objectToJson(ipMessage));
		//设置过期时间
		jedis.expire(IPMESSAGE, 60*60*24*7);
		logger.info("保存了一个ipmessage到redis"+ipMessage.toString());
	}

	/**
	 * 随机获得IpMessage
	 * @return
	 */
	public IPMessage getIpMessage(){
		if (getIpMessageNum() == 0) {
			return null;
		}
		//srandmember随机取出num个元素
		List<String> json = jedis.srandmember(IPMESSAGE, 1);
		logger.info(json);
		if (json.size() == 0 || json == null) {
			return null;
		}
		IPMessage ipMessage = JsonUtils.jsonToPojo(json.get(0), IPMessage.class);
		return ipMessage;
	}

	/**
	 * 返回剩余ipmessage的数量
	 * @return
	 */
	public synchronized long getIpMessageNum(){
		return jedis.scard(IPMESSAGE);
	}

	public void removeProxyIp(String json){
		jedis.srem(IPMESSAGE, json);
	}
	
	
	/**
	 * 在redis检查代理ip 
	 * @return 代理ip数量
	 */
	public void checkProxyIp(){
		int count = 0;
		Set<String> smembers = jedis.smembers(IPMESSAGE);
		Iterator<String> iterator = smembers.iterator();

		ExecutorService pool = Executors.newCachedThreadPool();

		while(iterator.hasNext()){
			count++;
			pool.submit(() ->{
				String next = iterator.next();
				IPMessage ipMessage = JsonUtils.jsonToPojo(next, IPMessage.class);
				boolean isTable = ipFilter.isTable(ipMessage);
				if (!isTable) {
					jedis.srem(IPMESSAGE, next);
					logger.info("从redis移除了一个代理ip"+ipMessage.toString());
				}else {
					logger.info(ipMessage.toString()+"是稳固的");
				}
			});
		}
		pool.shutdown();
		
		//等待线程结束		
		while(true){  
			if(pool.isTerminated()){  
				break;  
			}  
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}    
		}
	}	


}
