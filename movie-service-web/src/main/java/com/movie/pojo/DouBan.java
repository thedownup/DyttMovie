package com.movie.pojo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="douban")
public class DouBan implements Serializable{
	
	private int id;
	private String movieName;
	private String movieUrl;
	private String score;
	private String rank;
	private String movieIntroduce;
	
	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}
	@Column(name="movieintroduce")
	public String getMovieIntroduce() {
		return movieIntroduce;
	}
	@Column(name="moviename")
	public String getMovieName() {
		return movieName;
	}
	@Column(name="movieurl")
	public String getMovieUrl() {
		return movieUrl;
	}
	public String getRank() {
		return rank;
	}
	public String getScore() {
		return score;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setMovieIntroduce(String movieIntroduce) {
		this.movieIntroduce = movieIntroduce;
	}
	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}
	public void setMovieUrl(String movieUrl) {
		this.movieUrl = movieUrl;
	}
	public void setRank(String rank) {
		this.rank = rank;
	}
	public void setScore(String score) {
		this.score = score;
	}
	
	@Override
	public String toString() {
		return "DouBan [id=" + id + ", movieName=" + movieName + ", movieUrl=" + movieUrl + ", score=" + score
				+ ", rank=" + rank + ", movieIntroduce=" + movieIntroduce + "]";
	}
	
	
}
