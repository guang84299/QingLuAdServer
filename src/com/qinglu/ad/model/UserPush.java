package com.qinglu.ad.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_push")
public class UserPush {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private Long pushId;
	
	@Column(name = "username", length = 64)
	private String username;
	
	private int isClick;
	
	private int isDownload;
	
	private int isInstall;

	public UserPush(){}
	public UserPush(Long pushId, String username, int isClick,
			int isDownload,int isInstall) {
		super();
		this.pushId = pushId;
		this.username = username;
		this.isClick = isClick;
		this.isDownload = isDownload;
		this.isInstall = isInstall;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getPushId() {
		return pushId;
	}
	public void setPushId(Long pushId) {
		this.pushId = pushId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getIsClick() {
		return isClick;
	}
	public void setIsClick(int isClick) {
		this.isClick = isClick;
	}
	public int getIsDownload() {
		return isDownload;
	}
	public void setIsDownload(int isDownload) {
		this.isDownload = isDownload;
	}
	public int getIsInstall() {
		return isInstall;
	}
	public void setIsInstall(int isInstall) {
		this.isInstall = isInstall;
	}
	
	
	
}
