package com.movie.actionimpl;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.struts2.interceptor.RequestAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.movie.pojo.DouBan;
import com.movie.pojo.FilterTag;
import com.movie.pojo.HotMovie;
import com.movie.pojo.NObject;
import com.movie.pojo.Rolling;
import com.movie.pojo.SingleMovie;
import com.movie.pojo.User;
import com.movie.redis.HotMovieRedis;
import com.movie.redis.RollingRedis;
import com.movie.service.DouBanService;
import com.movie.service.SingleMovieService;
import com.movie.service.UserService;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/**
 * @author zjt
 * @Description: 控制页面跳转的
 */
@Controller("pageAction")
public class PageAction extends ActionSupport implements RequestAware,ModelDriven<FilterTag>{

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private SingleMovieService singleMovieService;
	@Autowired
	private HotMovieRedis hotMovieRedis;
	@Autowired
	private DouBanService douBanService;
	@Autowired
	private RollingRedis rollingRedis;
	@Autowired
	private UserService userService;

	private int num;
	private int page;
	private String uid;
	private String rd;
	
	private FilterTag filterTag;
	private Map<String, Object> request;
	public int getNum() {
		return num;
	}
	public int getPage() {
		return page;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public void setPage(int page) {
		this.page = page;
	}

	
	/**
	 * 首页
	 * @return
	 */
	public String index() {
		//初始化轮播图
		List<Rolling> rollings = rollingRedis.getRolling();
		request.put("rollings",rollings);
		//初始化热门电影
		List<HotMovie> hotMovies = hotMovieRedis.getHotMovie();
		request.put("hotMovies", hotMovies);
		return "index";
	}


	/**
	 * 电影
	 * @return
	 */
	public String movie() {
		//默认值
		if (page == 0) {
			page++;
		}
		
		setDefaultFilterTag();
		NObject nObject = singleMovieService.getMovies(page,num,filterTag);
		
		//设置分页总数
		request.put("pageNum", getPageNum(nObject.getNum(), 0));
		@SuppressWarnings("unchecked")
		List<SingleMovie> movies = (List<SingleMovie>) nObject.getObjects();
		for (SingleMovie movie : movies) {
			if (movie.getType().contains(" ")) {
				movie.setType(movie.getType().replaceAll(" ", "/"));
			}
		}
		request.put("movies", movies);
		request.put("filterTag", filterTag);

		request.put("page", page);

		return "movie";
	}
	
	
	private int getPageNum(int count,int pageNum){
		if (count % num == 0) {
			pageNum = count/num;
		}else {
			pageNum = count/num + 1;
		}
		return pageNum;
	}
	

	private void setDefaultFilterTag(){

		//用来对标签进行赋值
		if (filterTag == null) {
			filterTag.setYear("全部");
			filterTag.setScore("全部");
			filterTag.setType("全部");
			filterTag.setArea("全部");
		}else{
			if (filterTag.getYear() == null || filterTag.getYear().equals("")) {
				filterTag.setYear("全部");
			}
			if (filterTag.getScore() == null || filterTag.getScore().equals("")) {
				filterTag.setScore("全部");
			}
			if (filterTag.getType() == null || filterTag.getType().equals("")) {
				filterTag.setType("全部");
			}
			if (filterTag.getArea() == null || filterTag.getArea().equals("")) {
				filterTag.setArea("全部");
			}
		}
	}


	public String tv() {
		return "tv";
	}
	
	/**
	 * 他人页面
	 * @return
	 */
	public String otherzhuye() {
		
		boolean matches = Pattern.compile("^[-\\+]?[\\d]*$").matcher(uid).matches();
		if (matches == false) {
			return Action.ERROR;
		}

		User ouser = userService.getUserById(Integer.valueOf(uid));
		request.put("ouser", ouser);
		
	
		return "otherzhuye";
	}
	
	/**
	 *	豆瓣的页面 
	 * @return
	 */
	public String douban250() {
		if (page == 0) {
			page++;
		}
		
		List<DouBan> douBan = douBanService.getDouBan(page);
		request.put("page", page);
		request.put("doubans", douBan);
		
		return "douban250";
	}

	public String hotest() {
		return "hotest";
	}

	public String signin() {
		return "signin";
	}

	public String signup() {
		return "signup";
	}

	public String error(){
		return "error";
	}
	
	public String getUid() {
		return uid;
	}
	
	
	public String getRd() {
		return rd;
	}
	
	public void setRd(String rd) {
		this.rd = rd;
	}
	
	public void setUid(String uid) {
		this.uid = uid;
	}
	
	@Override
	public void setRequest(Map<String, Object> request) {
		this.request = request;
	}
	@Override
	public FilterTag getModel() {
		filterTag = new FilterTag();
		return filterTag;
	}
}
