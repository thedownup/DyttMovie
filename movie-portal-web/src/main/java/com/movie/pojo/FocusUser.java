package com.movie.pojo;

import java.io.Serializable;

/**
 * @author zjt
 * @Description: 用户关注表
 */
public class FocusUser implements Serializable {

	private static final long serialVersionUID = 1L;

	private int uid;
	private String touXianImg;

	public FocusUser(int uid, String touXianImg) {
		super();
		this.uid = uid;
		this.touXianImg = touXianImg;
	}

	public String getTouXianImg() {
		return touXianImg;
	}

	public int getUid() {
		return uid;
	}

	public void setTouXianImg(String touXianImg) {
		this.touXianImg = touXianImg;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	@Override
	public String toString() {
		return "FocusUser [uid=" + uid + ", touXianImg=" + touXianImg + "]";
	}

}
