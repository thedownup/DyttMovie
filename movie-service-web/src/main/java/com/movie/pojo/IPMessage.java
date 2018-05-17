package com.movie.pojo;

import java.io.Serializable;

/**
 * @author zjt
 * @Description: 代理ip的相关属性
 */
public class IPMessage implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String ipAdress;
	private int port;
	private String type;
	private float speed;
	private String life;
	
	public String getIpAdress() {
		return ipAdress;
	}
	public int getPort() {
		return port;
	}
	public String getType() {
		return type;
	}
	public float getSpeed() {
		return speed;
	}
	public String getLife() {
		return life;
	}
	public void setIpAdress(String ipAdress) {
		this.ipAdress = ipAdress;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setSpeed(float speed) {
		this.speed = speed;
	}
	public void setLife(String life) {
		this.life = life;
	}
	
	@Override
	public String toString() {
		return "IPMessage [ipAdress=" + ipAdress + ", port=" + port + ", type=" + type + ", speed=" + speed + ", life="
				+ life + "]";
	}
}
