package com.movie.pojo;

import java.io.Serializable;

public class HotMovie implements Serializable {

	private long hid;
	private String score;
	private int id;
	private String date;
	private String clarity;
	private String year;
	private String area;
	private String type;
	private boolean isMovie;
	private String movieName;
	private String movieImgUrl;

	public String getArea() {
		return area;
	}

	public String getClarity() {
		return clarity;
	}

	public String getDate() {
		return date;
	}

	public long getHid() {
		return hid;
	}

	public int getId() {
		return id;
	}

	public String getMovieImgUrl() {
		return movieImgUrl;
	}

	public String getMovieName() {
		return movieName;
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

	public boolean isMovie() {
		return isMovie;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public void setClarity(String clarity) {
		this.clarity = clarity;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public void setHid(long hid) {
		this.hid = hid;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setMovie(boolean isMovie) {
		this.isMovie = isMovie;
	}

	public void setMovieImgUrl(String movieImgUrl) {
		this.movieImgUrl = movieImgUrl;
	}

	public void setMovieName(String movieName) {
		this.movieName = movieName;
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
		return "HotMovie [hid=" + hid + ", score=" + score + ", id=" + id + ", date=" + date + ", clarity=" + clarity
				+ ", year=" + year + ", area=" + area + ", type=" + type + ", isMovie=" + isMovie + ", movieName="
				+ movieName + ", movieImgUrl=" + movieImgUrl + "]";
	}
}
