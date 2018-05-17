package com.movie.serviceimpl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message.RecipientType;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.movie.dao.UserDao;
import com.movie.pojo.User;
import com.movie.service.UserService;
import com.sun.mail.util.MailSSLSocketFactory;


@Service
public class UserServiceImpl implements UserService{

	private Logger logger = Logger.getLogger(UserServiceImpl.class);

	
	@Value("${touxian}")
	private String touxianurl;
	@Value("${beijin}")
	private String beijinurl;
	@Value("${STMP}")
	private String STMP;
	@Value("${HOST}")
	private String HOST;
	@Value("${SENDMAILADRESS}")
	private String SENDMAILADRESS;
	@Value("${DEBUG}")
	private String DEBUG;
	@Value("${MAILHOST}")
	private String MAILHOST;

	@Autowired
	private UserDao userDao;

	/**
	 * 登陆
	 */
	@Override
	public User checkUser(User user) {
		try {
			//密码进行MD5加密
			user.setPassWord(DigestUtils.md5DigestAsHex(user.getPassWord().getBytes()));
			User luser = userDao.login(user);
			if (luser != null) {
				logger.info("登陆了一个人"+user.toString());
				return luser; 
			}else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 注册
	 */
	@Override
	public String createUser(User user) {
		try {
			//密码进行MD5加密
			user.setPassWord(DigestUtils.md5DigestAsHex(user.getPassWord().getBytes()));
			user.setCreated(new Date());
			user.setUpdated(new Date());
			//设置默认的头像
			user.setTouXiangImg(touxianurl);
			//设置默认的背景
			user.setBackImg(beijinurl);
			userDao.save(user);
			logger.info("注册了一个人"+user.toString());
			return "注册成功";
		} catch (Exception e) {
			logger.error("注册发生错误");
			return "注册失败";
		}
	}

	@Override
	public void update(User user) {
		userDao.update(user);
	}

	@Override
	public void sendMail(User user,String token) throws Exception {
		// 创建Properties 类用于记录邮箱的一些属性
		Properties properties = new Properties();
		// 开启debug调试，以便在控制台查看
		properties.setProperty("mail.debug", "true"); 
		// 设置邮件服务器主机名
		properties.setProperty("mail.host", MAILHOST);
		// 发送服务器需要身份验证
		properties.setProperty("mail.smtp.auth", "true");
		// 发送邮件协议名称
		properties.setProperty("mail.transport.protocol", "smtp");
		// 此处填写你的账号
		properties.put("mail.user", SENDMAILADRESS);
		// 此处的密码就是前面说的16位STMP口令
		properties.put("mail.password", STMP);

		// 构建授权信息，用于进行SMTP进行身份验证
		Authenticator authenticator = new Authenticator() {

			protected PasswordAuthentication getPasswordAuthentication() {
				// 用户名、密码
				String userName = properties.getProperty("mail.user");
				String password = properties.getProperty("mail.password");
				return new PasswordAuthentication(userName, password);
			}
		};
		// 使用环境属性和授权信息，创建邮件会话
		Session mailSession = Session.getInstance(properties, authenticator);
		// 创建邮件消息
		MimeMessage message = new MimeMessage(mailSession);
		// 开启SSL加密，否则会失败
		MailSSLSocketFactory  mf = new MailSSLSocketFactory();
		mf.setTrustAllHosts(true);
		properties.put("mail.smtp.ssl.enable", "true");
		properties.put("mail.smtp.ssl.socketFactory", mf);

		InternetAddress from = new InternetAddress(properties.getProperty("mail.user"));
		message.setFrom(from);
		InternetAddress to = new InternetAddress("632443784@qq.com");
		message.setRecipient(RecipientType.TO, to);
		message.setSubject("账号激活邮件");  
		message.setSentDate(new Date()); 
		//发送邮件  
		///邮件的内容  
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  

		StringBuffer sb=new StringBuffer("<div style=\"width:660px;overflow:hidden;border-bottom:1px"
				+ " solid #bdbdbe;\"><div style=\"height:52px;overflow:hidden;border:1px solid "
				+ "#464c51;background:#353b3f url(http://www.lofter.com/rsc/img/email/hdbg.png);"
				+ "\"><a href=\"http://www.lofter.com?mail=qbclickbynoticemail_20120626_01\" "
				+ "target=\"_blank\" style=\"display:block;width:144px;height:34px;margin:10px 0 0"
				+ " 20px;overflow:hidden;text-indent:-2000px;background:url(http://www.lofter.com"
				+ "/rsc/img/email/logo.png) no-repeat;\">LOFTER</a></div>"+"<div style=\"padding:24px"
						+ " 20px;\">您好，"+user.getEmail()+"<br/><br/>"
								+ "欢迎使用电影天堂！<br/><br/>请点击下面链接激活账号，24小时生效，否则重新注册账号，链接只能使用一次，请尽快激活！</br>");  
		sb.append("<a href=\""+HOST+"validationMail_Mail?username=");  
		sb.append(user.getUserName());  
		sb.append("&token=");  
		sb.append(token);  
		sb.append("\">"+HOST+"validationMail_Mail?username=");  
		sb.append(user.getUserName());  
		sb.append("&token=");  
		sb.append(token);  
		sb.append("</a>"+"<br/>如果以上链接无法点击，请把上面网页地址复制到浏览器地址栏中打开<br/><br/><br/>LOFTER，专注兴趣，分享创作<br/>"+sdf.format(new Date())+ "</div></div>" );  
		message.setContent(sb.toString() , "text/html;charset=utf-8");
		Transport.send(message);
	}

	@Override
	public User getByName(String username) {
		return userDao.getByName(username);
	}

	@Override
	public String getUserTouXiangById(String id) {
		return userDao.getUserTouXiangById(id);
	}

	@Override
	public User getUserById(int uid) {
		return userDao.getUserById(uid);
	}

	@Override
	public boolean checkUserName(String userName) {
		return userDao.checkUserName(userName);
	}


}
