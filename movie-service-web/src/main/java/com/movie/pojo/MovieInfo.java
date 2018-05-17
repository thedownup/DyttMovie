package com.movie.pojo;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * @author zjt
 * @Description: 影片详情表
 */
@Entity
@Table(name="movieInfo")
public class MovieInfo implements Serializable{

	private static final long serialVersionUID = 1L;
	private int id;
	private String director;
	private String starring;
	private String writers;
	private String type;
	private String language;
	private String alias;
	private String rTime;
	private String movieIntroduce;
	private String link;
	@JsonIgnore
	private Movie movie;
	
	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}
	public String getDirector() {
		return director;
	}
	public String getStarring() {
		return starring;
	}
	public String getType() {
		return type;
	}
	public String getWriters() {
		return writers;
	}
	public String getrTime() {
		return rTime;
	}
	public String getLanguage() {
		return language;
	}
	public String getAlias() {
		return alias;
	}
	public String getMovieIntroduce() {
		return movieIntroduce;
	}
	public String getLink() {
		return link;
	}
	
	@OneToOne(fetch=FetchType.EAGER,cascade=CascadeType.ALL)
	@JoinColumn(name="mid")
	public Movie getMovie() {
		return movie;
	}

	public void setId(int id) {
		this.id = id;
	}
	public void setDirector(String director) {
		this.director = director;
	}
	public void setStarring(String starring) {
		this.starring = starring;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public void setMovieIntroduce(String movieIntroduce) {
		this.movieIntroduce = movieIntroduce;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public void setWriters(String writers) {
		this.writers = writers;
	}
	public void setrTime(String rTime) {
		this.rTime = rTime;
	}
	public void setMovie(Movie movie) {
		this.movie = movie;
	}
	
	@Override
	public String toString() {
		return "MovieInfo [id=" + id + ", director=" + director + ", starring=" + starring + ", writers=" + writers
				+ ", type=" + type + ", language=" + language + "alias=" + alias
				+ ", rTime=" + rTime + ", movieIntroduce=" + movieIntroduce + ", link=" + link
				+ ", movie=" + movie + "]";
	}
}
