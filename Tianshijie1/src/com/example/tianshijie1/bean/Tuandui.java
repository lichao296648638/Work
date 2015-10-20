package com.example.tianshijie1.bean;

import java.io.Serializable;

public class Tuandui implements Serializable {
	/**
	 * 团队信息写的类
	 */
	private static final long serialVersionUID = 1L;

	private String iv_touxiang;
	private String name;
	private String zhiwei;
	private String jianjie;

	public String getIv_touxiang() {
		return iv_touxiang;
	}

	public void setIv_touxiang(String iv_touxiang) {
		this.iv_touxiang = iv_touxiang;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getZhiwei() {
		return zhiwei;
	}

	public void setZhiwei(String zhiwei) {
		this.zhiwei = zhiwei;
	}

	public String getJianjie() {
		return jianjie;
	}

	public void setJianjie(String jianjie) {
		this.jianjie = jianjie;
	}
}
