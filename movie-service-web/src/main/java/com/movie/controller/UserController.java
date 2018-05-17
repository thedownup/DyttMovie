package com.movie.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.movie.pojo.MResult;
import com.movie.pojo.User;
import com.movie.service.UserService;
import com.movie.until.UserValidation;

/**
 * @author zjt
 * @Description: 管理用户的crud
 */
@Controller
@RequestMapping("/user")
public class UserController {

	private static final Logger logger = Logger.getLogger(UserController.class);

	@Value("${touxian}")
	private String touXian;
	@Value("${beijin}")
	private String beiJin;
	@Autowired
	private UserService userService;

	@InitBinder  
	protected void initBinder(WebDataBinder binder) {  
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));  
	}  

	@RequestMapping("/get")
	@ResponseBody
	public String getUser(@RequestParam(defaultValue="1")int page,@RequestParam(defaultValue="10")int num){
		return userService.getUsers(page, num);
	}

	@RequestMapping("/delete")
	@ResponseBody
	public MResult deleteUserById(int id){
		try {
			userService.deleteById(id);
			return MResult.build("删除成功");
		} catch (Exception e) {
			return MResult.build("删除失败");
		}
	}

	@RequestMapping(value="/update",method=RequestMethod.POST)
	@ResponseBody
	public MResult update(User user,String flag){
		
		
		//判断是否符合规定
		String result = UserValidation.userValidationUpdate(user, userService);
		if (!result.equals("true")) {
			return MResult.build(result);
		}
		//密码转MD5 说明改了密码
		if (flag.equals("true")) {
			user.setPassWord(DigestUtils.md5DigestAsHex(user.getPassWord().getBytes()));
		}
		//更新时间		
		user.setUpdated(new Date());
		try {
			userService.update(user);
			logger.info("管理系统进行了更新"+user.toString()+"----"+"密码是否修改 ->"+flag);
			return MResult.build("更新成功");
		} catch (Exception e) {
			e.printStackTrace();
			return MResult.build("更新失败"+e.getMessage());
		}
	}

	@RequestMapping(value="/create",method=RequestMethod.POST)
	@ResponseBody
	public MResult create(User user){

		if (user == null) {
			return MResult.build("不能全为空");
		}
		
		//判断是否符合规定
		String result = UserValidation.userValidationSave(user, userService);
		if (!result.equals("true")) {
			return MResult.build(result);
		}
		user.setPassWord(DigestUtils.md5DigestAsHex(user.getPassWord().getBytes()));
		user.setCreated(new Date());
		user.setUpdated(new Date());
		
		if (user.getTouXiangImg() == null || user.getTouXiangImg().equals("")) {
			user.setTouXiangImg(touXian);
		}
		
		if (user.getBackImg() == null || user.getBackImg().equals("")) {
			user.setBackImg(beiJin);
		}
		
		try {
			userService.save(user);
			logger.info("管理系统创建了一个用户"+user.toString());
			return MResult.build("创建用户成功");
		} catch (Exception e) {
			return MResult.build("创建用户失败");
		}
	}

}
