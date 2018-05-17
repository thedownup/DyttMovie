package com.movie.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "movie_user")
public class User implements Serializable {

	private int id;
	private String userName;
	private String passWord;
	private Date birthday;
	private int state;
	private short sex;
	private String signature;
	private String email;
	private String backImg;
	private String touXiangImg;
	private Date created;
	private Date updated;
	private Set<Comments> comments;
	private Set<MovieFlag> movieFlags;

	public String getBackImg() {
		return backImg;
	}

	public Date getBirthday() {
		return birthday;
	}

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, mappedBy = "user")
	public Set<Comments> getComments() {
		return comments;
	}

	public Date getCreated() {
		return created;
	}

	public String getEmail() {
		return email;
	}

	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, mappedBy = "user")
	public Set<MovieFlag> getMovieFlags() {
		return movieFlags;
	}

	public String getPassWord() {
		return passWord;
	}

	public short getSex() {
		return sex;
	}

	public String getSignature() {
		return signature;
	}

	public int getState() {
		return state;
	}

	public String getTouXiangImg() {
		return touXiangImg;
	}

	public Date getUpdated() {
		return updated;
	}

	public String getUserName() {
		return userName;
	}

	public void setBackImg(String backImg) {
		this.backImg = backImg;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public void setComments(Set<Comments> comments) {
		this.comments = comments;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setMovieFlags(Set<MovieFlag> movieFlags) {
		this.movieFlags = movieFlags;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public void setSex(short sex) {
		this.sex = sex;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public void setState(int state) {
		this.state = state;
	}

	public void setTouXiangImg(String touXiangImg) {
		this.touXiangImg = touXiangImg;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", userName=" + userName + ", passWord=" + passWord + ", birthday=" + birthday
				+ ", state=" + state + ", sex=" + sex + ", signature=" + signature + ", email=" + email
				+ ", touXiangImg=" + touXiangImg + ", created=" + created + ", updated=" + updated + "]";
	}
}
