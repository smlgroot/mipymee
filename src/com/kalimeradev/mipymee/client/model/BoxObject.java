package com.kalimeradev.mipymee.client.model;

import java.io.Serializable;

public class BoxObject implements Serializable {

	private Long year;
	private Long month;
	

	public BoxObject() {
	}


	public BoxObject(Long year, Long month) {
		super();
		this.year = year;
		this.month = month;
	}


	public Long getYear() {
		return year;
	}


	public void setYear(Long year) {
		this.year = year;
	}


	public Long getMonth() {
		return month;
	}


	public void setMonth(Long month) {
		this.month = month;
	}


}
