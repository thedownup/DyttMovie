package com.movie.pojo;

import java.io.Serializable;

/**
 * @author zjt
 * @Description: 用于展示最近浏览的用户
 */
public class RUser implements Serializable {

	private String uid;
	private String url;

	public String getUid() {
		return uid;
	}

	public String getUrl() {
		return url;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
