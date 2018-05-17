package com.movie.controller;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.movie.pojo.EasyUIDataGridResult;
import com.movie.pojo.MResult;
import com.movie.pojo.Movie;
import com.movie.service.MovieService;

/**
 * @author zjt
 * @Description: 电影方面
 */
@Controller
@RequestMapping("/movie")
public class MovieController {
	
	@Autowired
	private MovieService movieService;
	
	@RequestMapping(value="/get",method=RequestMethod.POST)
	@ResponseBody
	public EasyUIDataGridResult getMovies(int page,int num){
		EasyUIDataGridResult easyUIDataGridResult = movieService.getMovies(page, num);
		return easyUIDataGridResult;
	}
	
	@RequestMapping(value="/delete",method=RequestMethod.POST)
	@ResponseBody
	public MResult deleteMovie(int...  ids){
		if (ids.length == 0) {
			return MResult.build("请选择要删除的对象");
		}
		
		try {
			movieService.deleteById(ids);
			return MResult.build("删除电影成功");
		} catch (Exception e) {
			return MResult.build("删除电影成功"+e.getMessage());
		}
	}
	
	@RequestMapping(value="/update",method=RequestMethod.POST)
	@ResponseBody
	public MResult updateMovie(Movie movie){
		
		try {
			movieService.updateMovie(movie);
			return MResult.build("更新电影成功");
		} catch (Exception e) {
			return MResult.build("更新电影失败"+e.getMessage());
		}
	}

	
	@RequestMapping(value="/create",method=RequestMethod.POST)
	@ResponseBody
	public MResult saveMovie(Movie movie){
		String pattern = "^[\u4e00-\u9fa5_a-zA-Z0-9_\\/]+";
		if (movie.getMovieName().equals("")) {
			return MResult.build("电影名字不应该为空");
		}
		if (!Pattern.matches(pattern, movie.getType())) {
			return MResult.build("电影类型不符合要求格式为 ??/??/");
		}
		
		try {
			movieService.saveMovie(movie);
			return MResult.build("保存电影成功");
		} catch (Exception e) {
			return MResult.build("保存电影失败"+e.getMessage());
		}
	}
	
}
