package com.qinglu.ad.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "adplatfrom")
public class AdPlatfrom implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5682398801508874744L;
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	
	private int platfrom;//0 青露  1有米	2广点通

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getPlatfrom() {
		return platfrom;
	}

	public void setPlatfrom(int platfrom) {
		this.platfrom = platfrom;
	}
	
	
}
