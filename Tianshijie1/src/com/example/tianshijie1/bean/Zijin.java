package com.example.tianshijie1.bean;

import java.io.Serializable;

public class Zijin implements Serializable {
    private static final long serialVersionUID = 1L;
    private String xuhao;
    private String caozuo;
    private String jine;
    private String shijian;
    private String xiangmu;
    private String zhuangtia;
    /**
     * 功能添加Start
     * 需求编号:XQ1
     * 需要判断是否可以点击放款按钮，
     * 后台通过添加一个新字段
     * ( "agree_fangkuan": "0" //是否放款， 0：不放，1：放)来进行判断
     * 修改人：李超
     * 修改日期：2015-10-22
     */
    private String agree_fangkuan;
    //功能添加End
    private String t_status;/* 当状态值为500 时 判断操作状态的依据*/
    private String loanstatus;/* 状态值为600 700是判断操作的依据*/
    private String status;/* 状态值*/
    private String cid;
    private String pid;

    /**
     * 功能添加Start 需求编号XQ1
     */
    public String getAgree_fangkuan() {
        return agree_fangkuan;
    }

    public void setAgree_fangkuan(String agree_fangkuan) {
        this.agree_fangkuan = agree_fangkuan;
    }
    //功能添加End

    public String getT_status() {
        return t_status;
    }

    public void setT_status(String t_status) {
        this.t_status = t_status;
    }

    public String getLoanstatus() {
        return loanstatus;
    }

    public void setLoanstatus(String loanstatus) {
        this.loanstatus = loanstatus;
    }

    public String getXuhao() {
        return xuhao;
    }

    public void setXuhao(String xuhao) {
        this.xuhao = xuhao;
    }

    public String getCaozuo() {
        return caozuo;
    }

    public void setCaozuo(String caozuo) {
        this.caozuo = caozuo;
    }

    public String getJine() {
        return jine;
    }

    public void setJine(String jine) {
        this.jine = jine;
    }

    public String getShijian() {
        return shijian;
    }

    public void setShijian(String shijian) {
        this.shijian = shijian;
    }

    public String getXiangmu() {
        return xiangmu;
    }

    public void setXiangmu(String xiangmu) {
        this.xiangmu = xiangmu;
    }

    public String getZhuangtia() {
        return zhuangtia;
    }

    public void setZhuangtia(String zhuangtia) {
        this.zhuangtia = zhuangtia;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

}
