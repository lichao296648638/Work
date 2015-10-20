package com.example.tianshijie1.bean;

import java.io.Serializable;

public class MyMessage implements Serializable {
	/**
	 * 消息的bean
	 */
	private static final long serialVersionUID = 1L;
	private String msgfrom;
	private String isread;
	private String subject;
	private String dateline;
	private String pmid;

	public String getMsgfrom() {
		return msgfrom;
	}

	public void setMsgfrom(String msgfrom) {
		this.msgfrom = msgfrom;
	}

	public String getIsread() {
		return isread;
	}

	public void setIsread(String isread) {
		this.isread = isread;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getDateline() {
		return dateline;
	}

	public void setDateline(String dateline) {
		this.dateline = dateline;
	}

	public String getPmid() {
		return pmid;
	}

	public void setPmid(String pmid) {
		this.pmid = pmid;
	}
}
