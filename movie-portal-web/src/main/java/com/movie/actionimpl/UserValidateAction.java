package com.movie.actionimpl;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

/**
 * @author zjt
 * @Description: 登陆注册的验证码
 */
@Controller("userValidateAction")
public class UserValidateAction extends ActionSupport implements SessionAware{
	
	private static final Logger logger = Logger.getLogger(UserValidateAction.class);

	private static final long serialVersionUID = 1L;

	private static int WIDTH = 60;

	private Map<String, Object> session;
	private static int HEIGHT = 20;
	
	public String type;
	private String rd;
	
	private static String createRandom() {
		String str = "0123456789qwertyuiopasdfghjklzxcvbnm";

		char[] rands = new char[4];

		Random random = new Random();

		for (int i = 0; i < 4; i++) {
			rands[i] = str.charAt(random.nextInt(36));
		}

		return new String(rands);
	}

	private ByteArrayInputStream inputStream;

	private void drawBackground(Graphics g) {
		// 画背景
		g.setColor(new Color(0xDCDCDC));

		g.fillRect(0, 0, WIDTH, HEIGHT);

		// 随机产生 120 个干扰点

		for (int i = 0; i < 120; i++) {
			int x = (int) (Math.random() * WIDTH);
			int y = (int) (Math.random() * HEIGHT);
			int red = (int) (Math.random() * 255);
			int green = (int) (Math.random() * 255);
			int blue = (int) (Math.random() * 255);
			g.setColor(new Color(red, green, blue));
			g.drawOval(x, y, 1, 0);
		}
	}

	private void drawRands(Graphics g, String rands) {
		g.setColor(Color.BLACK);
		g.setFont(new Font(null, Font.ITALIC | Font.BOLD, 18));
		// 在不同的高度上输出验证码的每个字符
		g.drawString("" + rands.charAt(0), 1, 17);
		g.drawString("" + rands.charAt(1), 16, 15);
		g.drawString("" + rands.charAt(2), 31, 18);
		g.drawString("" + rands.charAt(3), 46, 16);

	}

	/**
	 * 注册和注册的验证码 
	 * @return
	 * @throws Exception
	 */
	@Override
	public String execute() throws Exception {
		HttpServletResponse response = ServletActionContext.getResponse();
		// 设置浏览器不要缓存此图片
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		String rands = createRandom();
		BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();
		// 产生图像
		drawBackground(g);
		drawRands(g, rands);
		// 结束图像 的绘制 过程， 完成图像
		g.dispose();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		ImageIO.write(image, "jpeg", outputStream);
		ByteArrayInputStream input = new ByteArrayInputStream(outputStream.toByteArray());
		this.setInputStream(input);
		if (type.equals("signup")) {
			logger.debug("注册验证码"+rands);
			session.put("signupCode", rands);
		}
		if (type.equals("signin")) {
			logger.debug("登陆验证码"+rands);
			session.put("signinCode", rands);
		}
		input.close();
		outputStream.close();
		return Action.SUCCESS;
	}
	
	

	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getRd() {
		return rd;
	}

	public void setRd(String rd) {
		this.rd = rd;
	}

	public ByteArrayInputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(ByteArrayInputStream inputStream) {
		this.inputStream = inputStream;
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
}
