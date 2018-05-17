package com.movie.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.movie.pojo.EasyUIDataGridResult;
import com.movie.pojo.HotMovie;
import com.movie.pojo.MResult;
import com.movie.reids.HotMovieRedis;

/**
 * @author zjt
 * @Description: 热门电影
 */
@Controller
@RequestMapping("/hotmovie")
public class HotMovieController {
	
	@Autowired
	private HotMovieRedis hotMovieRedis;
	
	@RequestMapping("/get")
	@ResponseBody
	public EasyUIDataGridResult getHotMovies(){
		
		EasyUIDataGridResult easyUIDataGridResult = hotMovieRedis.getHotMovie();
		return easyUIDataGridResult;
	}
	
	@RequestMapping(value="/save",method=RequestMethod.POST)
	@ResponseBody
	public MResult saveHotMovie(HotMovie hotMovie){
		
		try {
			hotMovieRedis.saveHotMovie(hotMovie);
			return MResult.build("保存成功");
		} catch (Exception e) {
			return MResult.build("保存失败"+e.getMessage());
		}
	}
	
	@RequestMapping(value="/delete",method=RequestMethod.POST)
	@ResponseBody
	public MResult deleteHotMovie(HotMovie hotMovie){
		try {
			hotMovieRedis.deleteHotMovie(hotMovie);
			return MResult.build("删除成功");
		} catch (Exception e) {
			return MResult.build("删除失败"+e.getMessage());
		}
	}
	
	@RequestMapping(value="/update",method=RequestMethod.POST)
	@ResponseBody
	public MResult updateHotMovie(double newScore,HotMovie hotMovie){
		try {
			hotMovieRedis.updateHotMovie(hotMovie, newScore);
			return MResult.build("更新成功");
		} catch (Exception e) {
			return MResult.build("更新失败"+e.getMessage());
		}
	}
}
