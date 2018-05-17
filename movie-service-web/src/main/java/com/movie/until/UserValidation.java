package com.movie.until;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.movie.pojo.User;
import com.movie.service.UserService;

/**
 * @author zjt
 * @Description: 用户验证
 */
public class UserValidation {

	public static String userValidationUpdate(User user,UserService userService){

		String result = validation0(user);

		if (!result.equals("true")) {
			return result;
		}

		return "true";
	}


	public static String userValidationSave(User user,UserService userService){

		String result = validation0(user);
		
		if (!result.equals("true")) {
			return result;
		}
		
		if (!userService.haveUserName(user.getUserName())) {
			return "名称已经被占用";
		}

		return "true";
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

	public static boolean isUrl(String url){
		if (url == null || url.equals(""))
			return true;
		String regEx1 = "^([hH][tT]{2}[pP]://|[hH][tT]{2}[pP][sS]://)(([A-Za-z0-9-~]+).)+([A-Za-z0-9-~\\/])+$";
		Pattern p;
		Matcher m;
		p = Pattern.compile(regEx1);
		m = p.matcher(url);
		if (m.matches())
			return true;
		else
			return false;
	}
	
	private static String validation0(User user){
		
		if (user.getUserName() == null || user.getUserName().equals("")) {
			return "用户名不应该为空";
		}
		
		if (user.getPassWord() == null || user.getPassWord().equals("")) {
			return "密码不应该为空";
		}
		
		if (user.getSignature() != null) {
			if (user.getSignature() == null || user.getSignature().length() > 150) {
				return "个性签名字段太长";
			}
		}
		
		if (user.getEmail() == null || !isEmail(user.getEmail())) {
			return "邮箱格式不对";
		}
		
		if (!(user.getSex() == 0 || user.getSex() == 1)) {
			return "性别错误 只能在0或1 0为男 1为女";
		}

		if (!(user.getState() == 0 || user.getState() == 1)) {
			return "验证状态 只能在0或1 0为未验证 1为验证";
		}
		
		if (user.getTouXiangImg() != null) {
			if (!isUrl(user.getTouXiangImg())) {
				return "头像地址不是一个url";
			}
		}
		
		if (user.getBackImg() != null) {
			if (!isUrl(user.getBackImg())) {
				return "背景地址不是一个url";
			}
		}
		
		return "true";
	}

}
