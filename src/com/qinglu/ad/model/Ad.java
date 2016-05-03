package com.qinglu.ad.model;

import java.io.File;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ad")
public class Ad {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "company",  length = 64) 
	private String company;//公司
	
	private int type;//广告类型
	
	@Column(name = "picPath",  length = 64) 
	private String picPath;//图片路径
	
	@Column(name = "downloadPath",  length = 128) 
	private String downloadPath;//下载路径
	
	private int showNum;//展示次数
	
	private int clickNum;//点击次数
	
	public Ad()
	{
		
	}
	
	public Ad(String company, int type, String picPath,String downloadPath) {
		super();
		this.company = company;
		this.type = type;
		this.picPath = picPath;
		this.downloadPath = downloadPath;
		this.showNum = 0;
		this.clickNum = 0;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getPicPath() {
		return picPath;
	}
	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}

	public String getDownloadPath() {
		return downloadPath;
	}

	public void setDownloadPath(String downloadPath) {
		this.downloadPath = downloadPath;
	}

	public int getShowNum() {
		return showNum;
	}

	public void setShowNum(int showNum) {
		this.showNum = showNum;
	}

	public int getClickNum() {
		return clickNum;
	}

	public void setClickNum(int clickNum) {
		this.clickNum = clickNum;
	}
	
	
	
}
