package com.movie.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.movie.pojo.EasyUIDataGridResult;
import com.movie.pojo.MResult;
import com.movie.pojo.MovieInfo;
import com.movie.service.MovieInfoService;

/**
 * @author zjt
 * @Description: 用来给电影详情页面提供数据
 */
@Controller
@RequestMapping("/movieInfo")
public class MovieInfoController {

	@Autowired
	private MovieInfoService movieInfoService;

	@RequestMapping("/get")
	@ResponseBody
	public EasyUIDataGridResult getMovieInfos(int page,int num){

		return movieInfoService.getMovieInfos(page, num);
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public MResult saveMovieInfo(MovieInfo movieInfo){
		
		try {
			movieInfoService.saveMovieInfo(movieInfo);
			return MResult.build("电影详情保存完毕");
		} catch (Exception e) {
			return MResult.build("电影详情保存失败"+e.getMessage());
		}
	}
	
	@RequestMapping("/update")
	@ResponseBody
	public MResult updateMovieInfo(MovieInfo movieInfo){
		
		try {
			movieInfoService.updateMovieInfo(movieInfo);
			return MResult.build("电影详情更新成功");
		} catch (Exception e) {
			return MResult.build("电影详情更新失败"+e.getMessage());
		}
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public MResult deleteMovieInfoById(int... ids){
		
		try {
			movieInfoService.deleteById(ids);
			return MResult.build("电影详情删除成功");
		} catch (Exception e) {
			return MResult.build("电影详情删除失败"+e.getMessage());
		}
	}
	
	@RequestMapping("search")
	@ResponseBody
	public EasyUIDataGridResult getMovieInfoByMovieId(int mid){
		return movieInfoService.getMovieInfoByMovieId(mid);
	}

}
