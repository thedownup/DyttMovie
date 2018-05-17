package com.movie.pojo;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * @author zjt
 * @Description: 影片详情表
 */
@Entity
@Table(name = "movieInfo")
public class MovieInfo implements Serializable {

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
	private Movie movie;
	private Set<Comments> comments;
	private Set<MovieFlag> movieFlag;

	public String getAlias() {
		return alias;
	}

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, mappedBy = "movieInfo")
	public Set<Comments> getComments() {
		return comments;
	}

	public String getDirector() {
		return director;
	}

	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}

	public String getLanguage() {
		return language;
	}

	public String getLink() {
		return link;
	}

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "mid")
	public Movie getMovie() {
		return movie;
	}

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, mappedBy = "movieInfo")
	public Set<MovieFlag> getMovieFlag() {
		return movieFlag;
	}

	public String getMovieIntroduce() {
		return movieIntroduce;
	}

	public String getrTime() {
		return rTime;
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

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public void setComments(Set<Comments> comments) {
		this.comments = comments;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	public void setMovieFlag(Set<MovieFlag> movieFlag) {
		this.movieFlag = movieFlag;
	}

	public void setMovieIntroduce(String movieIntroduce) {
		this.movieIntroduce = movieIntroduce;
	}

	public void setrTime(String rTime) {
		this.rTime = rTime;
	}

	public void setStarring(String starring) {
		this.starring = starring;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setWriters(String writers) {
		this.writers = writers;
	}

	@Override
	public String toString() {
		return "MovieInfo [id=" + id + ", director=" + director + ", starring=" + starring + ", writers=" + writers
				+ ", type=" + type + ", language=" + language + ", alias=" + alias + ", rTime=" + rTime
				+ ", movieIntroduce=" + movieIntroduce + ", link=" + link + "]";
	}

}
