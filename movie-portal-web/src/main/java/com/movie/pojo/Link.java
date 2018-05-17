package com.movie.pojo;

import java.io.Serializable;

public class Link implements Serializable {

	private static final long serialVersionUID = 1L;
	private String linkName;
	private String linkUrl;

	public String getLinkName() {
		return linkName;
	}

	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

}
