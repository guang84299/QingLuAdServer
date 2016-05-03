package com.qinglu.ad.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "device")
public class Device implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	
	@Column(name = "deviceId", nullable = false, length = 64, unique = true) 
	private String deviceId;//imei
	
	@Column(name = "phoneNumber",  length = 12) 
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
	
	@Column(name = "subscriberId",  length = 64) 
	private String subscriberId;//用户唯一标识，比如GSM网络的IMSI编号
	
	@Column(name = "packageName",  length = 64) 
	private String packageName;//包名
	
	@Column(name = "appName",  length = 64) 
	private String appName;//应用名字
	
	@Column(name = "model",  length = 64) 
	private String model;//手机型号
	
	@Column(name = "sys_release",  length = 64) 
	private String release;//系统版本
	
	public Device()
	{
		
	}

	public Device(String deviceId, String phoneNumber,
			String networkOperatorName, String simSerialNumber,
			String networkCountryIso, String networkOperator,
			String networkType, String location, int phoneType,
			String subscriberId, String packageName, String appName,
			String model, String release) {
		super();
		this.deviceId = deviceId;
		this.phoneNumber = phoneNumber;
		this.networkOperatorName = networkOperatorName;
		this.simSerialNumber = simSerialNumber;
		this.networkCountryIso = networkCountryIso;
		this.networkOperator = networkOperator;
		this.networkType = networkType;
		this.location = location;
		this.phoneType = phoneType;
		this.subscriberId = subscriberId;
		this.packageName = packageName;
		this.appName = appName;
		this.model = model;
		this.release = release;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getSubscriberId() {
		return subscriberId;
	}

	public void setSubscriberId(String subscriberId) {
		this.subscriberId = subscriberId;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
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
	
	
	
}
