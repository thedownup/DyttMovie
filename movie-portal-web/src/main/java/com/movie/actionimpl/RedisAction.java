package com.movie.actionimpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.movie.pojo.NObject;
import com.movie.pojo.RUser;
import com.movie.redis.RecentlyRedis;
import com.movie.service.RedisService;
import com.movie.untils.JsonUtils;
import com.opensymphony.xwork2.Action;


/**
 * @author zjt
 * @Description: 从redis中获取数据等操作
 */
@Controller
public class RedisAction {

	private static final Logger logger = Logger.getLogger(RedisAction.class);
	
	@Autowired
	private RedisService redisService;
	@Autowired
	private RecentlyRedis recentlyRedis;

	private int uid;
	private int mid;
	private String movieName;
	private String jsonData;

	public String saveRecentlyMovies(){
		
		try {
			redisService.saveRecentlyMovies(uid, mid, movieName);
			jsonData = "1";
		} catch (Exception e) {
			jsonData = "reids保存发送错误";
		}
		return Action.SUCCESS;
	}
	
	public String getRecentlyMovies(){
		
		List<String> movies = redisService.getRecentlyMovies(uid);
		jsonData = JsonUtils.objectToJson(movies);
		return Action.SUCCESS;
	}
	
	/**
	 * 获得最近浏览过的用户
	 * @param mid
	 */
	public String getRecentlyWatchUser(){
		try {
			NObject nObject = new NObject();
			List<RUser> lists = new ArrayList<RUser>();
			if (recentlyRedis.getRecentlyWatchUser(String.valueOf(mid)) == null || recentlyRedis.getRecentlyWatchUser(String.valueOf(mid)).size() == 0) {
				jsonData = null;
				return Action.SUCCESS;
			}
			HashMap<String,String> hashMap = recentlyRedis.getRecentlyWatchUser(String.valueOf(mid));
			Set<Entry<String,String>> entrySet = hashMap.entrySet();
			for (Entry<String, String> entry : entrySet) {
				RUser rUser = new RUser();
				rUser.setUid(entry.getKey());
				rUser.setUrl(entry.getValue());
				lists.add(rUser);
			}
			
			nObject.setObjects(lists);
			nObject.setNum(lists.size());
			jsonData = JsonUtils.objectToJson(nObject);
			logger.debug("有人浏览了电影id为"+mid+"的电影");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Action.SUCCESS;
	}
	
	/**
	 * 记录浏览过的人
	 * @param mid
	 * @param uid
	 */
	public String recordUser(){
		recentlyRedis.recordUser(String.valueOf(mid), String.valueOf(uid));
		jsonData  = "1";
		return Action.SUCCESS;
	}
	
	public int getMid() {
		return mid;
	}
	
	public String getMovieName() {
		return movieName;
	}

	public int getUid() {
		return uid;
	}
	
	public String getJsonData() {
		return jsonData;
	}

	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}

	public void setMid(int mid) {
		this.mid = mid;
	}
	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}
	
	public void setUid(int uid) {
		this.uid = uid;
	}
	
	
}
