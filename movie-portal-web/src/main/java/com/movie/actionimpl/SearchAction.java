package com.movie.actionimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.RequestAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.movie.pojo.Movie;
import com.movie.pojo.MovieInfo;
import com.movie.pojo.NObject;
import com.movie.service.MovieService;
import com.opensymphony.xwork2.Action;

@Controller
public class SearchAction implements RequestAware{
	
	@Autowired
	private MovieService movieService;

	private int page;
	private int num;
	private String q;
	private Map<String, Object> request;

	
	public String search(){
		
		if (page == 0) {
			page = 1;
		}
		
		//没有输入直接跳过
		if (q == null || q.equals("")) {
			return "success";
		}
		
		NObject nObject = movieService.searchMovies(page, num, q);
		
		@SuppressWarnings("unchecked")
		List<Movie> movies = (List<Movie>) nObject.getObjects();
		if (movies.size() == 0) {
			return "success";
		}
		
		List<MovieInfo> movieInfos = new ArrayList<MovieInfo>();
		for (Movie movie : movies) {
			movieInfos.add(movie.getMovieInfo());
			
		}
		
		request.put("movies", movies);
		request.put("movieInfos", movieInfos);
		request.put("pageNum", getPageNum(nObject.getNum(), 0));
		request.put("page", page);
		request.put("q", q);
		
		return Action.SUCCESS;
	}
	
	private int getPageNum(int count,int pageNum){
		if (count % num == 0) {
			pageNum = count/num;
		}else {
			pageNum = count/num + 1;
		}
		return pageNum;
	}
	
	public int getPage() {
		return page;
	}
	
	public int getNum() {
		return num;
	}
	
	public String getQ() {
		return q;
	}
	
	public void setPage(int page) {
		this.page = page;
	}
	
	public void setNum(int num) {
		this.num = num;
	}
	
	public void setQ(String q) {
		this.q = q;
	}

	@Override
	public void setRequest(Map<String, Object> request) {
		this.request = request;
	}
}
