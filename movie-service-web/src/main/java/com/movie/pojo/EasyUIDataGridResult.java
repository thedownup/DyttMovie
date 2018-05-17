package com.movie.pojo;

import java.io.Serializable;
import java.util.List;
/**
 * 
 * @author zjt
 * @Description: 查询list所用
 * @date 2018年2月25日 下午1:59:38
 *
 */
public class EasyUIDataGridResult implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private long total;
	private List<?> rows;
	
	public long getTotal() {
		return total;
	}
	public List<?> getRows() {
		return rows;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public void setRows(List<?> rows) {
		this.rows = rows;
	}
}
