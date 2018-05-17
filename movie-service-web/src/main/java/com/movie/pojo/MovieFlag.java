package com.movie.pojo;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="movie_flag")
public class MovieFlag {
	
	private int id;
	private short wantSee;
	private short seeBefore;
	private short likeSee;
	private User user;
	private MovieInfo movieInfo;
	
	public MovieFlag(){};
	
	public MovieFlag(int id,short wantSee,short seeBefore,short likeSee){
		this.id = id;
		this.wantSee = wantSee;
		this.seeBefore = seeBefore;
		this.likeSee = likeSee;
	}
	
	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}
	public short getLikeSee() {
		return likeSee;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="mid")
	public MovieInfo getMovieInfo() {
		return movieInfo;
	}
	public short getSeeBefore() {
		return seeBefore;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="uid")
	public User getUser() {
		return user;
	}
	public short getWantSee() {
		return wantSee;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setLikeSee(short likeSee) {
		this.likeSee = likeSee;
	}
	
	public void setMovieInfo(MovieInfo movieInfo) {
		this.movieInfo = movieInfo;
	}
	public void setSeeBefore(short seeBefore) {
		this.seeBefore = seeBefore;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public void setWantSee(short wantSee) {
		this.wantSee = wantSee;
	}
	
	@Override
	public String toString() {
		return "MovieFlag [id=" + id + ", wantSee=" + wantSee + ", seeBefore=" + seeBefore + ", likeSee=" + likeSee
				+ "]";
	}
	
}
