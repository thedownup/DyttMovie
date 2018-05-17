package com.movie.pojo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "movie")
public class SingleMovie implements Serializable {
	private int id;
	private String date;
	private String clarity;
	private String area;
	private String year;
	private String type;
	private String score;
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

	@Id
	@GeneratedValue
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

	@Column(name = "ismovie")
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
		return "Movie [id=" + id + ", date=" + date + ", clarity=" + clarity + ", type=" + type + ", movieName="
				+ movieName + ", movieImgUrl=" + movieImgUrl + "]";
	}

}
