package com.movie.redis;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.movie.service.UserService;
import com.movie.untils.JsonUtils;

import redis.clients.jedis.Jedis;

/**
 * @author zjt
 * @Description: 记录最近观看电影的人
 */
@Component
public class RecentlyRedis extends BaseRedis{
	
	private static final String RECENTLYWATCH = "RecentlyWatch";
	private static final long RNUM = 8;
	
	@Autowired
	private UserService userService;
	
	public HashMap<String, String> getRecentlyWatchUser(String mid){
		Jedis jedis = getJedis();
		//最大设置数量为8个
		long num = 0;
		if (jedis.hlen(RECENTLYWATCH) == null || jedis.hlen(RECENTLYWATCH) == 0) { jedis.close();return null;}
		else {num = jedis.hlen(RECENTLYWATCH);}
		/*if (num > 8) {
			Set<String> set = jedis.hkeys(RECENTLYWATCH);
			Iterator<String> iterator = set.iterator();
			long count = num - RNUM;
			while(iterator.hasNext()){
				count--;
				set.remove(iterator.next());
				if (count == 0) {
					break;
				}
			}
		}*/
		
		if (jedis.hget(RECENTLYWATCH, mid) == null) {
			return null;
		}
		String json = jedis.hget(RECENTLYWATCH, mid);
		@SuppressWarnings("unchecked")
		HashMap<String, String> hashMap = JsonUtils.jsonToPojo(json, HashMap.class);
		
		jedis.close();
		
		return hashMap;
	}
	
	
	@SuppressWarnings("unchecked")
	public void recordUser(String mid,String uid) {
		Jedis jedis = getJedis();
		
		//得user的头像
		String touxiang = userService.getUserTouXiangById(uid);
		//先获取
		//判断是否是第一次访问没有
		if (jedis.hget(RECENTLYWATCH, mid) == null) {
			Map<String, String> hashmap = new HashMap<String, String>();
			hashmap.put(uid, touxiang);
			jedis.hset(RECENTLYWATCH, mid, JsonUtils.objectToJson(hashmap));
			jedis.close();
			return;
		}
		
		String befRecordUser = jedis.hget(RECENTLYWATCH, mid);
		HashMap hashMap = JsonUtils.jsonToPojo(befRecordUser, HashMap.class);
		hashMap.put(uid, touxiang);
		jedis.hset(RECENTLYWATCH, mid, JsonUtils.objectToJson(hashMap));
		jedis.close();
		
	}
}
