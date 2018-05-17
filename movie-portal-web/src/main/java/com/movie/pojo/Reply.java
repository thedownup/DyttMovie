package com.movie.pojo;

import java.util.List;

/**
 * @author zjt
 * @Description: 用来回复和获取转成json用的
 */
public class Reply {

	private int num;
	private List<ReplyInner> replyInners;

	public int getNum() {
		return num;
	}

	public List<ReplyInner> getReplyInners() {
		return replyInners;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public void setReplyInners(List<ReplyInner> replyInners) {
		this.replyInners = replyInners;
	}

}
