package com.movie.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author zjt
 * @Description: 记录上次数据爬到的页数,判断是否要在爬
 */
@Entity
@Table(name="page_flag")
public class PageFlag implements Serializable{

	private static final long serialVersionUID = 1L;
	private int id;
	private String movieType;
	private int page;
	private Date time;
	
	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}
	public String getMovieType() {
		return movieType;
	}
	public int getPage() {
		return page;
	}
	public Date getTime() {
		return time;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setMovieType(String movieType) {
		this.movieType = movieType;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	
	@Override
	public String toString() {
		return "PageFlag [id=" + id + ", movieType=" + movieType + ", page=" + page + ", time=" + time + "]";
	}
}
