package com.movie.pojo;

import java.util.concurrent.Future;
/**
 * @author zjt
 * @Description: 用来存储ipmessage和future
 */
public class IpAndFut{
	private IPMessage ipMessage;
	private Future<Boolean> future;
	public IPMessage getIpMessage() {
		return ipMessage;
	}
	public Future<Boolean> getFuture() {
		return future;
	}
	public void setIpMessage(IPMessage ipMessage) {
		this.ipMessage = ipMessage;
	}
	public void setFuture(Future<Boolean> future) {
		this.future = future;
	}
	
}

