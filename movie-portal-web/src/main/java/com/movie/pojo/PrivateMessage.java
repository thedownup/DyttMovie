package com.movie.pojo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author zjt
 * @Description: 私信
 */
@Entity
@Table(name="private_message")
public class PrivateMessage {

	private int id;
	private int uid;
	private int fid;
	private int sid;
	private int rid;
	private byte messageType;
	private String message;
	private String sendDate;

	public PrivateMessage() {
	}

	public PrivateMessage(int id, int uid, int fid, int sid, int rid, byte messageType, String message,
			String sendDate) {
		super();
		this.id = id;
		this.uid = uid;
		this.fid = fid;
		this.sid = sid;
		this.rid = rid;
		this.messageType = messageType;
		this.message = message;
		this.sendDate = sendDate;
	}

	public int getFid() {
		return fid;
	}

	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}

	public String getMessage() {
		return message;
	}

	public byte getMessageType() {
		return messageType;
	}

	public int getRid() {
		return rid;
	}

	public String getSendDate() {
		return sendDate;
	}

	public int getSid() {
		return sid;
	}

	public int getUid() {
		return uid;
	}

	public void setFid(int fid) {
		this.fid = fid;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setMessageType(byte messageType) {
		this.messageType = messageType;
	}

	public void setRid(int rid) {
		this.rid = rid;
	}

	public void setSendDate(String sendDate) {
		this.sendDate = sendDate;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}
	
	@Override
	public String toString() {
		return "PrivateMessage [id=" + id + ", uid=" + uid + ", fid=" + fid + ", sid=" + sid + ", rid=" + rid
				+ ", messageType=" + messageType + ", message=" + message + ", sendDate=" + sendDate + "]";
	}

}
