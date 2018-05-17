package com.movie.pojo;

import java.io.Serializable;

/**
 * @author zjt
 * @Description: 进行过滤标签
 */
public class FilterTag implements Serializable {

	private String year;
	private String score;
	private String area;
	private String type;

	public String getArea() {
		return area;
	}

	public String getScore() {
		return score;
	}

	public String getType() {
		return type;
	}

	public String getYear() {
		return year;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setYear(String year) {
		this.year = year;
	}

	@Override
	public String toString() {
		return "FilterTag [year=" + year + ", score=" + score + ", area=" + area + ", type=" + type + "]";
	}
}
