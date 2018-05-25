package com.movie.pojo;

import java.io.Serializable;

public class ReplyInner implements Serializable {
	

	private int id;
	private String date;
	private String name;
	private String message;
	private String touxiangUrl;

	public String getDate() {
		return date;
	}

	public String getMessage() {
		return message;
	}

	public String getName() {
		return name;
	}

	public String getTouxiangUrl() {
		return touxiangUrl;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	
	public void setDate(String date) {
		this.date = date;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setTouxiangUrl(String touxiangUrl) {
		this.touxiangUrl = touxiangUrl;
	}

	@Override
	public String toString() {
		return "ReplyInner [date=" + date + ", name=" + name + ", message=" + message + ", touxiangUrl=" + touxiangUrl
				+ "]";
	}
}
