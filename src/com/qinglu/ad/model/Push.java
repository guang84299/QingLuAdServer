package com.qinglu.ad.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "push")
public class Push {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private Long adId;
	
	private int type;//0:push messg 1:push spot
	
	private int userType;//0:所有在线 1：单人 2：包用户 3:其他
	
	private int sendNum;//推送人数
	
	private int showNum;//展示次数
	
	private int clickNum;//点击次数
	
	private int downloadNum;//下载次数
	
	private int installNum;//安装次数
	
	@Column(name = "created_date", updatable = false)
    private Date createdDate = new Date();
	
	public Push(){}

	public Push(Long adId, int type, int userType, int sendNum, int showNum, int clickNum,
			int downloadNum, int installNum) {
		super();
		this.adId = adId;
		this.type = type;
		this.userType = userType;
		this.sendNum = sendNum;
		this.showNum = showNum;
		this.clickNum = clickNum;
		this.downloadNum = downloadNum;
		this.installNum = installNum;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getAdId() {
		return adId;
	}

	public void setAdId(Long adId) {
		this.adId = adId;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getUserType() {
		return userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
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

	public int getDownloadNum() {
		return downloadNum;
	}

	public void setDownloadNum(int downloadNum) {
		this.downloadNum = downloadNum;
	}

	public int getInstallNum() {
		return installNum;
	}

	public void setInstallNum(int installNum) {
		this.installNum = installNum;
	}

	public int getSendNum() {
		return sendNum;
	}

	public void setSendNum(int sendNum) {
		this.sendNum = sendNum;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	
	
}
