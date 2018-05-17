package com.movie.untils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * @author zjt
 * @Description: 时间的转换
 */
public class DateUntil {
	
	public static String formData(Date date){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String format = simpleDateFormat.format(date);
		return format;
	}
	
}
