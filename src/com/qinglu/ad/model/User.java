/*
 * Copyright (C) 2010 Moduad Co., Ltd.
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */
package com.qinglu.ad.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;


/** 
 * This class represents the basic user object.
 *
 * @author Sehwan Noh (devnoh@gmail.com)
 */
@Entity
@Table(name = "user")
public class User implements Serializable {

    private static final long serialVersionUID = 4733464888738356502L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
   // subscriberId 用户唯一标识，比如GSM网络的IMSI编号
    @Column(name = "username", nullable = false, length = 64, unique = true)
    private String username;

    @Column(name = "password", length = 64)
    private String password;
 
    @Column(name = "created_date", updatable = false)
    private Date createdDate = new Date();

    @Column(name = "updated_date")
    private Date updatedDate;

    @Transient
    private boolean online;
    
    //设备相关
    @Column(name = "deviceId",  length = 64) 
	private String deviceId;//imei
	
	@Column(name = "phoneNumber",  length = 64) 
	private String phoneNumber;//手机号码
	
	@Column(name = "networkOperatorName",  length = 64) 
	private String networkOperatorName;//运营商名称
	
	@Column(name = "simSerialNumber",  length = 64) 
	private String simSerialNumber;//sim卡序列号
	
	@Column(name = "networkCountryIso",  length = 64) 
	private String networkCountryIso;//sim卡所在国家
	
	@Column(name = "networkOperator",  length = 64) 
	private String networkOperator;//运营商编号
	
	@Column(name = "networkType",  length = 12) 
	private String networkType;//网络类型
	
	@Column(name = "location",  length = 64) 
	private String location;//移动终端的位置
	/**
     * 移动终端的类型
     * PHONE_TYPE_CDMA 手机制式为CDMA，电信 2
     * PHONE_TYPE_GSM 手机制式为GSM，移动和联通 1
     * PHONE_TYPE_NONE 手机制式未知 0
     */
	//@Column(name = "phoneType") 
	private int phoneType;//
				
	@Column(name = "model",  length = 64) 
	private String model;//手机型号
	
	@Column(name = "sys_release",  length = 64) 
	private String release;//系统版本
	
	@Column(name = "province",  length = 64) 
	private String province;//省份
	
	@Column(name = "city",  length = 64) 
	private String city;//城市  
	
	@Column(name = "district",  length = 64) 
	private String district;//区县 
	
	@Column(name = "street",  length = 64) 
	private String street;//街道
    

    public User() {
    }

    public User(final String username) {
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getNetworkOperatorName() {
		return networkOperatorName;
	}

	public void setNetworkOperatorName(String networkOperatorName) {
		this.networkOperatorName = networkOperatorName;
	}

	public String getSimSerialNumber() {
		return simSerialNumber;
	}

	public void setSimSerialNumber(String simSerialNumber) {
		this.simSerialNumber = simSerialNumber;
	}

	public String getNetworkCountryIso() {
		return networkCountryIso;
	}

	public void setNetworkCountryIso(String networkCountryIso) {
		this.networkCountryIso = networkCountryIso;
	}

	public String getNetworkOperator() {
		return networkOperator;
	}

	public void setNetworkOperator(String networkOperator) {
		this.networkOperator = networkOperator;
	}

	public String getNetworkType() {
		return networkType;
	}

	public void setNetworkType(String networkType) {
		this.networkType = networkType;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getPhoneType() {
		return phoneType;
	}

	public void setPhoneType(int phoneType) {
		this.phoneType = phoneType;
	}


	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getRelease() {
		return release;
	}

	public void setRelease(String release) {
		this.release = release;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	@Override
    public boolean equals(Object o) {
        if (!(o instanceof User)) {
            return false;
        }

        final User obj = (User) o;
        if (username != null ? !username.equals(obj.username)
                : obj.username != null) {
            return false;
        }
        if (!(createdDate.getTime() == obj.createdDate.getTime())) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 29 * result + (username != null ? username.hashCode() : 0);
        result = 29 * result
                + (createdDate != null ? createdDate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this,
                ToStringStyle.MULTI_LINE_STYLE);
    }

}
