package com.movie.redis;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.stereotype.Repository;

import com.movie.pojo.Rolling;
import com.movie.untils.JsonUtils;

import redis.clients.jedis.Jedis;


@Repository
public class RollingRedis extends BaseRedis{
	
	private static final String ROLLINGNAME = "Rolling";
	
	public List<Rolling> getRolling(){
		
		Jedis jedis = getJedis();
		// 获取所有key value
	    Map<String, String> all = jedis.hgetAll(ROLLINGNAME);
	    
	    Set<Entry<String,String>> entrySet = all.entrySet();
	    
	    List<Rolling> rollings = new ArrayList<Rolling>();
	    
	    for (Entry<String, String> entry : entrySet) {

	    	Rolling rolling = JsonUtils.jsonToPojo(entry.getValue(), Rolling.class);
	    	rollings.add(rolling);
		}
	    
	    jedis.close();
	    return rollings;
	}
}
