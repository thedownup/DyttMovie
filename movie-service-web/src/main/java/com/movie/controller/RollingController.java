package com.movie.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.movie.pojo.EasyUIDataGridResult;
import com.movie.pojo.MResult;
import com.movie.pojo.Rolling;
import com.movie.reids.RollingRedis;

@Controller
@RequestMapping("rolling")
public class RollingController {
	
	private static final Logger logger = Logger.getLogger(RollingController.class);
	
	@Autowired
	private RollingRedis rollingRedis;
	
	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping(value="/get")
	@ResponseBody
	public EasyUIDataGridResult getRollings(){
		EasyUIDataGridResult easyUIDataGridResult = rollingRedis.getRolling();
		return easyUIDataGridResult;
	}
	
	@RequestMapping(value="/save")
	@ResponseBody
	public MResult saveRolling(Rolling rolling){
		
		logger.info(rolling.toString());
		
		try {
			rollingRedis.saveRolling(rolling);
			return MResult.build("保存成功");
		} catch (Exception e) {
			return MResult.build("保存失败"+e.getMessage());
		}
	}
	
	@RequestMapping(value="/delete",method=RequestMethod.POST)
	@ResponseBody
	public MResult deleteById(int id){

		try {
			rollingRedis.deleteRolling(id);
			return MResult.build("删除成功");
		} catch (Exception e) {
			return MResult.build("删除失败"+e.getMessage());
		}
	}
	
	@RequestMapping(value="/update",method=RequestMethod.POST)
	@ResponseBody
	public MResult updateRolling(Rolling rolling){
		
		try {
			rollingRedis.updateRolling(rolling);
			return MResult.build("更新成功");
		} catch (Exception e) {
			return MResult.build("更新失败"+e.getMessage());
		}
	}
	
}
