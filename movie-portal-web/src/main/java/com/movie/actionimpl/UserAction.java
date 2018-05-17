package com.movie.actionimpl;


import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;

import com.movie.pojo.FocusUser;
import com.movie.pojo.User;
import com.movie.service.FocusUserService;
import com.movie.service.PrivateMessageService;
import com.movie.service.UserService;
import com.movie.untils.DateUntil;
import com.opensymphony.xwork2.Action;

import redis.clients.jedis.JedisPool;


/**
 * @author zjt
 * @Description: 控制用户登陆注册 个人页面等
 */
@Controller
public class UserAction extends BaseAction<User>{

	private final static Logger logger = Logger.getLogger(UserAction.class);

	
	private String username;
	private String token;
	private String jsonData;
	private String newPassword;
	private String code;//验证码
	public File file;// 上传文件
	public String fileContentType;// 上传文件类型
	public String fileFileName;// 上传文件名



	@Value("${PATHICONIMG}")
	private String PATHICONS;
	@Value("${FileDomain}")
	private String fileDomain;
	@Autowired
	private UserService userService;
	//邮件验证用
	@Autowired
	private JedisPool jedisPool;
	@Autowired
	private FocusUserService focusUserService;
	@Autowired
	private PrivateMessageService privateMessageService;

	/**
	 * 登陆
	 * @return
	 */
	public String signin() {
		
		if (model == null) {
			return Action.SUCCESS;
		}

		if (code == null || session.get("signinCode") == null || !session.get("signinCode").equals(code)) {
			request.put("signupError", "验证码错误");
			jsonData = "验证码错误";
			return Action.SUCCESS;
		}
		
		if (userService.checkUser(model) == null) {
			request.put("loginError", "账号或密码错误");
			jsonData = "账号或密码错误";
			return Action.SUCCESS;
		}else{
			jsonData = "登陆成功";
			//添加到session
			User user = userService.getByName(model.getUserName());
			logger.info("登陆了一位"+model.toString());
			//取出完整的用户信息
			session.put("user", user);
			return Action.SUCCESS;
		}
	}


	/**
	 * 退出登陆
	 * @return
	 */
	public String signout(){
		if (session.get("user") != null) {
			session.remove("user");
		}
		return "signout";
	}


	/**
	 * 注册
	 * @return
	 */
	public String signup(){
		boolean email = isEmail(model.getEmail());

		if (model == null) {
			return Action.SUCCESS;
		}

		if (code == null || session.get("signupCode") == null || !session.get("signupCode").equals(code)) {
			request.put("signupError", "验证码错误");
			jsonData = "验证码错误";
			return Action.SUCCESS;
		}
		if (email == false) {
			request.put("signupError", "邮箱不合法");
			jsonData = "邮箱不合法";
			return Action.SUCCESS;
		}
		if (model.getPassWord().length() < 6) {
			request.put("signupError", "密码要大于等于6位");
			jsonData = "密码要大于等于6位";
			return Action.SUCCESS;
		}
		if (model.getUserName().length() < 4) {
			request.put("signupError", "名称要大于等于6位");
			jsonData = "名称要大于等于6位";
			return Action.SUCCESS;
		}
		if (userService.checkUserName(model.getUserName())) {
			request.put("signupError", "名称已经被注册");
			jsonData = "名称已经被注册";
			return Action.SUCCESS;
		}
		
		try {
			jsonData =  userService.createUser(model);
			jsonData = "注册成功";
			return Action.SUCCESS;
		} catch (Exception e) {
			jsonData = "注册发生错误";
			return Action.SUCCESS;
		}
	}

	/**
	 * 返回个人中心
	 * @return
	 */
	public String zhuye() {
		User user = (User) session.get("user");
		User newUser = userService.getByName(user.getUserName());
		List<FocusUser> focusUsers = focusUserService.getFocusUser(user.getId());
		request.put("focusUsers", focusUsers);
		session.put("filedomain", fileDomain);
		session.put("user", newUser);
		if (user.getBirthday()!= null) {
			request.put("birthday", DateUntil.formData(newUser.getBirthday()));
		}
		return "zhuye";
	}

	/**
	 * 返回个人设置
	 * @return
	 */
	public String setting() {
		User user = (User) session.get("user");
		User newUser = userService.getByName(user.getUserName());
		session.put("filedomain", fileDomain);
		session.put("user", newUser);
		if (user.getBirthday()!= null) {
			request.put("birthday", DateUntil.formData(newUser.getBirthday()));
		}
		return "setting";
	}

	/**
	 * 私信页面
	 * @return
	 */
	public String sixin(){

		User user = (User) session.get("user");
		
		List<User> recentlyUser = privateMessageService.getRecentlyUser(user.getId());
		//取出聊天过的对象
		request.put("recentlyUser", recentlyUser);

		//开启私信对话
		if (model.getUserName() != null && model.getTouXiangImg() != null && model.getId() != 0) {
			request.put("suser", model);
		} 
		
		return "sixin";
	}

	/**
	 * 发送邮件
	 */
	public String sendMail(){
		try {
			if (session.get("user") == null) {
				jsonData = "清先登录";
			}else {
				User user = (User) session.get("user");
				String nowtoken = DigestUtils.md5DigestAsHex(String.valueOf(System.currentTimeMillis()).getBytes());
				userService.sendMail(user,nowtoken);
				//设置24小时有效
				jedisPool.getResource().set(String.valueOf(user.getUserName()), nowtoken);
				jedisPool.getResource().expire(String.valueOf(user.getUserName()), 60*5*60*12);
				jsonData = "发送邮件成功";
			}
			return Action.SUCCESS;
		} catch (Exception e) {
			jsonData = "发送邮件失败";
			e.printStackTrace();
			return Action.SUCCESS;
		}
	}

	/**
	 * 邮箱激活
	 * @return
	 */
	public String validationMail(){
		if (token.equals(jedisPool.getResource().get(username))) {
			//激活
			User user = userService.getByName(username);
			user.setState(1);
			userService.update(user);
			if (session.get("user") != null) {
				User usera = (User) session.get("user");
				usera.setState(1);
				usera.setUpdated(new Date());
				session.put("user", usera);
			}
			jsonData = "激活成功";
		}else {
			jsonData = "激活失败";
		}
		return Action.SUCCESS;
	}

	/**
	 * 基本资料修改
	 * @return
	 */
	public String updateUser(){
		try {
			User user = (User) session.get("user");

			//个性签名
			user.setSignature(model.getSignature());
			//生日
			user.setBirthday(model.getBirthday());
			Date date = new Date();
			//转换一下data格式
			request.put("birthday", DateUntil.formData(date));

			//不存在就创建
			File file = new File(PATHICONS);
			if (!file.exists()) {
				file.mkdir();
			}
			if (this.file != null) {
				FileUtils.copyFile(this.file, new File(file,fileFileName));
				user.setTouXiangImg(PATHICONS+"/"+fileFileName);
			}
			user.setUpdated(date);
			//1 女 0 男		
			user.setSex(model.getSex());
			userService.update(user);
			//重新查数据库
			User user2 = userService.getByName(user.getUserName());
			session.put("user", user2);
			jsonData = "修改成功";
		} catch (Exception e) {
			e.printStackTrace();
			jsonData = "修改失败";
		}
		return Action.SUCCESS;
	}


	/**
	 * 修改密码
	 * @return
	 */
	public String updatePassword() {

		try {
			User user = userService.getByName(((User) session.get("user")).getUserName());
			if (DigestUtils.md5DigestAsHex(model.getPassWord().getBytes()).equals(user.getPassWord())) {
				user.setPassWord(DigestUtils.md5DigestAsHex(newPassword.split(",")[0].trim().getBytes()));
				userService.update(user);
				jsonData = "修改成功";
			}else {
				jsonData = "密码错误";
			}
		} catch (Exception e) {
			jsonData = "修改失败";
		}
		return Action.SUCCESS;
	}


	public static boolean isEmail(String string) {
		if (string == null)
			return false;
		String regEx1 = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
		Pattern p;
		Matcher m;
		p = Pattern.compile(regEx1);
		m = p.matcher(string);
		if (m.matches())
			return true;
		else
			return false;
	}

	public String getUsername() {
		return username;
	}

	public String getToken() {
		return token;
	}

	public String getJsonData() {
		return jsonData;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public File getFile() {
		return file;
	}

	public String getFileContentType() {
		return fileContentType;
	}

	public String getFileFileName() {
		return fileFileName;
	}

	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
