package com.movie.reids;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.movie.ipfilter.IpFilter;
import com.movie.pojo.IPMessage;
import com.movie.until.JsonUtils;

import redis.clients.jedis.Jedis;

/**
 * @author zjt
 * @Description: redis的常用操作
 */
@Component
public class RedisHelper extends BaseRedis{

	private static final Logger logger = Logger.getLogger(RedisHelper .class);

	@Autowired
	private IpFilter ipFilter;
	private static final String IPMESSAGE = "IPMESSAGE";

	/**
	 * 插入可以用的代理ip
	 * @param ipMessage
	 */
	public void insertIpMessageToRedis(IPMessage ipMessage){
		Jedis jedis = getJedis();
		//从池中获取redis
		getJedis().sadd(IPMESSAGE, JsonUtils.objectToJson(ipMessage),JsonUtils.objectToJson(ipMessage));
		//设置过期时间
		jedis.expire(IPMESSAGE, 60*60*24*7);
		logger.info("保存了一个ipmessage到redis"+ipMessage.toString());
		jedis.close();
	}

	/**
	 * 随机获得IpMessage
	 * @return
	 */
	public IPMessage getIpMessage(){
		Jedis jedis = getJedis();
		if (getIpMessageNum() == 0) {
			return null;
		}
		//srandmember随机取出num个元素
		List<String> json = jedis.srandmember(IPMESSAGE, 1);
		logger.info(json);
		if (json.size() == 0 || json == null) {
			jedis.close();
			return null;
		}
		IPMessage ipMessage = JsonUtils.jsonToPojo(json.get(0), IPMessage.class);
		jedis.close();
		return ipMessage;
	}

	/**
	 * 返回剩余ipmessage的数量
	 * @return
	 */
	public synchronized long getIpMessageNum(){
		Jedis jedis = getJedis();
		return jedis.scard(IPMESSAGE);
	}

	public void removeProxyIp(String json){
		Jedis jedis = getJedis();
		jedis.srem(IPMESSAGE, json);
	}
	
	
	/**
	 * 在redis检查代理ip 
	 * @return 代理ip数量
	 */
	public void checkProxyIp(){
		Jedis jedis = getJedis();
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
			} finally {
				jedis.close();
			}
		}
	}	
	
	/**
	 * 删除某个键
	 * @param name
	 */
	public void del(String name){
		Jedis jedis = getJedis();
		jedis.del(name);
		jedis.close();
	}
}
