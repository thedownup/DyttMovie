package com.movie.reids;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.movie.pojo.EasyUIDataGridResult;
import com.movie.pojo.Rolling;
import com.movie.until.JsonUtils;

import redis.clients.jedis.Jedis;


@Component
public class RollingRedis extends BaseRedis{
	
	private static final String ROLLINGNAME = "Rolling";
	private static final String ROLLINGID = "rid";
	
	/**
	 * 保存首页滚动图的
	 * @param rolling
	 */
	public void saveRolling(Rolling rolling){
		
		Jedis jedis = getJedis();
		
		if ((jedis.get(ROLLINGID) == null)) {
			jedis.set(ROLLINGID, "1");
		}
		//设置id

		int id =  Integer.valueOf(jedis.get(ROLLINGID));
		rolling.setId(id);
		
		jedis.hset(ROLLINGNAME,String.valueOf(id),JsonUtils.objectToJson(rolling));

		//完成后增加id
		jedis.incr(ROLLINGID);
		
		jedis.close();
	}

	/**
	 * 对rolling的更新
	 * @param rolling
	 */
	public void updateRolling(Rolling rolling){
		Jedis jedis = getJedis();
		if (jedis.hget(ROLLINGNAME,String.valueOf(rolling.getId())) == null) {
			jedis.close();
			return;
		}
		
		jedis.hset(ROLLINGNAME, String.valueOf(rolling.getId()), JsonUtils.objectToJson(rolling));
		jedis.close();
	}
	
	/**
	 * 删除id号
	 * @param id
	 */
	public void deleteRolling(int id){
		Jedis jedis = getJedis();
		jedis.hdel(ROLLINGNAME, String.valueOf(id));
		jedis.close();
	}
	
	public EasyUIDataGridResult getRolling(){
		
		Jedis jedis = getJedis();		
		EasyUIDataGridResult easyUIDataGridResult = new EasyUIDataGridResult();
		
		// 获取所有key value
	    Map<String, String> all = jedis.hgetAll(ROLLINGNAME);
	    
	    Set<Entry<String,String>> entrySet = all.entrySet();
	    
	    List<Rolling> rollings = new ArrayList<Rolling>();
	    
	    for (Entry<String, String> entry : entrySet) {
	    	rollings.add(JsonUtils.jsonToPojo(entry.getValue(), Rolling.class));
		}
	    
	    
	    easyUIDataGridResult.setTotal(entrySet.size());
	    easyUIDataGridResult.setRows(rollings);
	    jedis.close();
		return easyUIDataGridResult;
	}
}
