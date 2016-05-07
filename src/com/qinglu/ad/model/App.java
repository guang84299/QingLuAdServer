package com.qinglu.ad.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "app")
public class App implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3622583868311646621L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	
	private Long userId;
	
	@Column(name = "name",  length = 64) 
	private String name;//应用名字
	
	@Column(name = "packageName",  length = 128) 
	private String packageName;//包名
	
	public App()
	{
		
	}

	public App(Long userId, String name, String packageName) {
		super();
		this.userId = userId;
		this.name = name;
		this.packageName = packageName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	
	
	
}
