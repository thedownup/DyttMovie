package com.movie.pojo;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author zjt
 * @Description: 首页电影轮播图
 */
public class Rolling implements Serializable {

	private int id;
	private int mid;
	private String bigImageUrl;

	public String getBigImageUrl() {
		return bigImageUrl;
	}

	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}

	public int getMid() {
		return mid;
	}

	public void setBigImageUrl(String bigImageUrl) {
		this.bigImageUrl = bigImageUrl;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setMid(int mid) {
		this.mid = mid;
	}

}
