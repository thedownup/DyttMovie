package com.movie.pojo;

import java.io.Serializable;

public class HotMovie extends Movie implements Serializable{

	
	private long hid;
	private String score;

	public long getHid() {
		return hid;
	}

	public void setHid(long hid) {
		this.hid = hid;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	
}
