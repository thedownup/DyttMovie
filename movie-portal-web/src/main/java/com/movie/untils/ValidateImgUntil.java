package com.movie.untils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

/**
 * @author zjt
 * @Description: 生成验证码图片
 */
public class ValidateImgUntil {

	public static void generateImg(HttpServletResponse response,ServletActionContext context) throws IOException{
		response.setHeader("Pragma", "No-cache");  
		response.setHeader("Cache-Control", "no-cache");  
		response.setDateHeader("Expires", 0);  
		//定义验证码框的大小  
		int width=60,height=20;  
		//生成干扰线背景色等  
		BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);  
		Graphics g =image.getGraphics();  
		Random random = new Random();  
		g.setColor(new Color(122,123,100));  
		g.fillRect(0, 0, width, height);  
		g.setFont(new Font("Time New Roman",Font.PLAIN,18));  
		for(int i=0;i<200;i++){  
			int x=random.nextInt(width);  
			int y=random.nextInt(height);  
			int x1= random.nextInt(12);  
			int y1=random.nextInt(12);  
			g.drawLine(x, y, x+x1, y1);  
		}  
		String sRand="";  
		for(int i=0;i<4;i++){  
			String rand = String.valueOf(random.nextInt(10));  
			sRand+=rand;  
			g.setColor(new Color(30+random.nextInt(160),40+random.nextInt(17),40+random.nextInt(180)));  
			g.drawString(rand, 13*i+6, 16);  
		}  
		//根据Random函数自动生成4个数字并将此数字存入session，在登陆验证控制器从session中获取这String与输入的比较  
		context.getSession().put("yzm", sRand);  
		g.dispose();  
		ImageIO.write(image, "JPEG", response.getOutputStream());  
	}  

}
