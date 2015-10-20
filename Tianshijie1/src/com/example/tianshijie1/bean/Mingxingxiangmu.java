package com.example.tianshijie1.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class Mingxingxiangmu implements Serializable {

	/**
	 * 明星项目的数据的bean
	 */
	private static final long serialVersionUID = 1L;

	private String id;
	private String collect;// 收藏数
	private String name;
	private String city_val;// 城市
	private String status_val;// 融资状态
	private String sy_time;// 剩余时间
	private String copy_number;// 份数
	private String banner;// 项目大图
	private String image;// 项目缩略图
	private String loan_amount;// 项目融资金额
	private String jindu;// 融资进度
	private String zongjia;// 总价格
	private String shijian;
	private String loan_share;// 让出股份
	private String version;// 是否为实体项目
	private String user_id;
	private String is_sc;// 是否收藏
	private String notime;// 一个时间
	private String url;// 用于分享的url
	private String pinglunnum;// 评论的数量
	private String copy_price;// 单价万元
	private String copy_yuan_price;// 单价元
	private String summary;// 项目的简介
	private String invent_amount;// 完成已筹
	private String get_amount;//预购已筹

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCollect() {
		return collect;
	}

	public void setCollect(String collect) {
		this.collect = collect;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCity_val() {
		return city_val;
	}

	public void setCity_val(String city_val) {
		this.city_val = city_val;
	}

	public String getStatus_val() {
		return status_val;
	}

	public void setStatus_val(String status_val) {
		this.status_val = status_val;
	}

	public String getSy_time() {
		return sy_time;
	}

	public void setSy_time(String sy_time) {
		this.sy_time = sy_time;
	}

	public String getCopy_number() {
		return copy_number;
	}

	public void setCopy_number(String copy_number) {
		this.copy_number = copy_number;
	}

	public String getBanner() {
		return banner;
	}

	public void setBanner(String banner) {
		this.banner = banner;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getLoan_amount() {
		return loan_amount;
	}

	public void setLoan_amount(String loan_amount) {
		this.loan_amount = loan_amount;
	}

	public String getJindu() {
		return jindu;
	}

	public void setJindu(String jindu) {
		this.jindu = jindu;
	}

	public String getZongjia() {
		return zongjia;
	}

	public void setZongjia(String zongjia) {
		this.zongjia = zongjia;
	}

	public String getShijian() {
		return shijian;
	}

	public void setShijian(String shijian) {
		this.shijian = shijian;
	}

	public String getLoan_share() {
		return loan_share;
	}

	public void setLoan_share(String loan_share) {
		this.loan_share = loan_share;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getIs_sc() {
		return is_sc;
	}

	public void setIs_sc(String is_sc) {
		this.is_sc = is_sc;
	}

	public String getNotime() {
		return notime;
	}

	public void setNotime(String notime) {
		this.notime = notime;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPinglunnum() {
		return pinglunnum;
	}

	public void setPinglunnum(String pinglunnum) {
		this.pinglunnum = pinglunnum;
	}

	public String getCopy_price() {
		return copy_price;
	}

	public void setCopy_price(String copy_price) {
		this.copy_price = copy_price;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getCopy_yuan_price() {
		return copy_yuan_price;
	}

	public void setCopy_yuan_price(String copy_yuan_price) {
		this.copy_yuan_price = copy_yuan_price;
	}

	public String getInvent_amount() {
		return invent_amount;
	}

	public void setInvent_amount(String invent_amount) {
		this.invent_amount = invent_amount;
	}

	public String getGet_amount() {
		return get_amount;
	}

	public void setGet_amount(String get_amount) {
		this.get_amount = get_amount;
	}

}
