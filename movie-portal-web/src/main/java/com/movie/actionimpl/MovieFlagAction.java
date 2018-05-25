package com.movie.actionimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.movie.pojo.MovieFlag;
import com.movie.pojo.NObject;
import com.movie.service.MovieFlagService;
import com.movie.service.MovieService;
import com.movie.untils.JsonUtils;
import com.opensymphony.xwork2.Action;

@Controller
public class MovieFlagAction extends BaseAction<MovieFlag> {

	@Autowired
	private MovieFlagService movieFlagService;
	@Autowired
	private MovieService movieService;
	
	private String jsonData;

	private int mid;
	private int uid;
	
	private int page;
	
	/**
	 * 获得
	 * @return
	 */
	public String getLikeMovies(){

		if (page == 0) {
			page++;
		}
		
		NObject nObject = movieService.getLikeMovies(uid, page, 8);
		session.put("likePage", page);
		jsonData = JsonUtils.objectToJson(nObject);
		
		
		return Action.SUCCESS;
	}
	
	public String saveMovieFlag(){
		
		try {
			movieFlagService.saveMovieFlag(uid, mid, model);
			jsonData = "1";
		} catch (Exception e) {
			e.printStackTrace();
			jsonData = "0";
		}
		
		return Action.SUCCESS;
	}
	
	/**
	 * 获得电影三个选项的标记
	 * @return
	 */
	public String getMovieFlag(){
		
		try {
			if (movieFlagService.get(uid, mid) == null) {
				jsonData = "";
				return Action.SUCCESS;
			}
			MovieFlag movieFlag = movieFlagService.get(uid, mid);
			jsonData = JsonUtils.objectToJson(movieFlag);
			
		} catch (Exception e) {
			e.printStackTrace();
			jsonData = "";
		}
		
		return Action.SUCCESS;
	}
	
	
	
	public String getJsonData() {
		return jsonData;
	}
	
	public int getMid() {
		return mid;
	}

	public int getUid() {
		return uid;
	}
	
	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public void setMid(int mid) {
		this.mid = mid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}
	
	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}
	
}
