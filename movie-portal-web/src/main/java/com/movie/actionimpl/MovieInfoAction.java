package com.movie.actionimpl;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.RequestAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.movie.pojo.Link;
import com.movie.pojo.Movie;
import com.movie.pojo.MovieInfo;
import com.movie.service.MovieFlagService;
import com.movie.service.MovieService;
import com.movie.service.SingleMovieService;
import com.movie.untils.JsonUtils;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
/**
 * @author zjt
 * @Description: 显示电影的详细信息
 */
@Controller
public class MovieInfoAction extends ActionSupport implements RequestAware{

	private static final Logger logger = Logger.getLogger(MovieFlagAction.class);

	private static final long serialVersionUID = 1L;


	@Autowired
	private MovieService movieService;
	@Autowired
	private MovieFlagService movieFlagService;
	@Autowired
	private SingleMovieService singleMovieService;


	private String mid;
	private Map<String, Object> request;


	public String showMovieInfo(){
		boolean matches = Pattern.compile("^[-\\+]?[\\d]*$").matcher(mid).matches();
		if (matches == false) {
			return Action.ERROR;
		}

		Movie movie = movieService.getMovieAndMovieInfo(Integer.valueOf(mid));
		if (movie == null) {
			logger.debug("没有这个id对应的电影id="+mid);
			return Action.ERROR;
		}
		
		if (movie.getType() != null && movie.getType().contains(" ")) {
			movie.setType(movie.getType().replaceAll(" ", "/"));
		}
		
		if (movie.getArea() != null && movie.getArea().contains(" ")) {
			movie.setArea(movie.getArea().replaceAll(" ", "/"));
		}

		MovieInfo movieInfo = movie.getMovieInfo();
		if (movieInfo.getStarring() != null) {
			movieInfo.setStarring(movieInfo.getStarring().replaceAll((char)12288 + "", " ").replaceAll("\\s+", " / "));
		}
		String linkJson = movieInfo.getLink();
		List<Link> listLink = JsonUtils.jsonToList(linkJson, Link.class);

		Map<Integer, String> recentlyMovies = singleMovieService.getRecentlyMovie();
		
		request.put("recentlyMovies", recentlyMovies);
		request.put("movie", movie);
		request.put("links", listLink);
		request.put("movieInfo", movie.getMovieInfo());
		request.put("mid", mid);
		request.put("likeSee", movieFlagService.getLikeSee(movieInfo.getId()));
		request.put("seeBefore", movieFlagService.getSeeBefore(movieInfo.getId()));
		request.put("wantSee", movieFlagService.getWantSee(movieInfo.getId()));
		request.put("wonderfulMovie", singleMovieService.getWonderfulMovies(movie.getType().trim()));

		//喜欢看的人数
		request.put("linkeSeeNum", movieFlagService.getLikeSee(Integer.valueOf(mid)));
		//想看的人数		
		request.put("wantSeeNum", movieFlagService.getWantSee(Integer.valueOf(mid)));
		//看过的人数		
		request.put("seeBeforeNum", movieFlagService.getSeeBefore(Integer.valueOf(mid)));
		
		
		return Action.SUCCESS;
	}

	public String getMid() {
		return mid;
	}


	public void setMid(String mid) {
		this.mid = mid;
	}

	@Override
	public void setRequest(Map<String, Object> request) {
		this.request = request;
	}

}
