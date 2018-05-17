package com.movie.pojo;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author zjt
 * @Description: 影片表
 */
@Entity
@Table(name="movie")
public class Movie implements Serializable{

	private static final long serialVersionUID = 1L;
	private int id;
	private String date;
	private String clarity;
	private String year;
	private String area;
	private String type;
	private String score;
	private boolean isMovie;
	private String movieName;
	private String movieImgUrl;
	@JsonIgnore
	private Set<MovieInfo> movieInfos;

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
	@Column(name="movieimgurl",length=60)
	public String getMovieImgUrl() {
		return movieImgUrl;
	}
	@OneToMany(mappedBy="movie",fetch=FetchType.LAZY,cascade=CascadeType.ALL)
	public Set<MovieInfo> getMovieInfos() {
		return movieInfos;
	}
	@Column(name="moviename", length=20)
	public String getMovieName() {
		return movieName;
	}
	public String getType() {
		return type;
	}

	public String getScore() {
		return score;
	}
	
	@Column(name="ismovie")
	public boolean isMovie() {
		return isMovie;
	}
	
	public void setMovie(boolean isMovie) {
		this.isMovie = isMovie;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public void setClarity(String clarity) {
		this.clarity = clarity;
	}

	public String getYear() {
		return year;
	}
	public String getArea() {
		return area;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public void setArea(String area) {
		this.area = area;
	}

	public void setDate(String date) {
		this.date = date;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setMovieImgUrl(String movieImgUrl) {
		this.movieImgUrl = movieImgUrl;
	}
	public void setMovieInfos(Set<MovieInfo> movieInfos) {
		this.movieInfos = movieInfos;
	}
	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}
	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Movie [id=" + id + ", date=" + date + ", clarity=" + clarity + ", year=" + year + ", area=" + area
				+ ", type=" + type + ", score=" + score + ", movieName=" + movieName + ", movieImgUrl=" + movieImgUrl
				+ "]";
	}

}
