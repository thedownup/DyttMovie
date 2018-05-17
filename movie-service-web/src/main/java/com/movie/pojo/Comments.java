package com.movie.pojo;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author zjt
 * @Description: 用户评论表
 */
@Entity
@Table(name="comments")
public class Comments {
	
	private int id;
	private String message;
	private String sendDate;
	private MovieInfo movieInfo;
	private User user;
	
	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}

	public String getMessage() {
		return message;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="mid")
	public MovieInfo getMovieInfo() {
		return movieInfo;
	}

	@ManyToOne()
	@JoinColumn(name="uid")
	public User getUser() {
		return user;
	}
	
	public String getSendDate() {
		return sendDate;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	public void setMovieInfo(MovieInfo movieInfo) {
		this.movieInfo = movieInfo;
	}
	public void setSendDate(String sendDate) {
		this.sendDate = sendDate;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	@Override
	public String toString() {
		return "Comments [id=" + id + ", message=" + message + ", sendDate=" + sendDate + ", movieInfo=" + movieInfo
				+ "]";
	}
}
