package com.movie.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * @author zjt
 * @Description: 用来存放数量和bean的
 */
public class NObject implements Serializable {

	private int num;
	private List<?> objects;

	public int getNum() {
		return num;
	}

	public List<?> getObjects() {
		return objects;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public void setObjects(List<?> objects) {
		this.objects = objects;
	}

}
