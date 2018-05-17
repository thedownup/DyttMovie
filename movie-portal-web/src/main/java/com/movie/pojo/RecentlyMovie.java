package com.movie.pojo;

import java.io.Serializable;

public class RecentlyMovie implements Serializable {

	private int mid;
	private String movieName;

	public int getMid() {
		return mid;
	}

	public String getMovieName() {
		return movieName;
	}

	public void setMid(int mid) {
		this.mid = mid;
	}

	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}

}
