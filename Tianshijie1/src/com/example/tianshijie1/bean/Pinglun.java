package com.example.tianshijie1.bean;

import java.io.Serializable;

public class Pinglun implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String username;
	private String dateline;
	private String avatar;
	private String content;
	private String pinglunxiangmu;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getDateline() {
		return dateline;
	}

	public void setDateline(String dateline) {
		this.dateline = dateline;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPinglunxiangmu() {
		return pinglunxiangmu;
	}

	public void setPinglunxiangmu(String pinglunxiangmu) {
		this.pinglunxiangmu = pinglunxiangmu;
	}

}
