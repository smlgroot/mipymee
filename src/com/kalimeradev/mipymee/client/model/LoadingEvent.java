package com.kalimeradev.mipymee.client.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class LoadingEvent implements Serializable {
	private int id;
	private boolean isLoading;

	public LoadingEvent() {

	}

	public LoadingEvent(int id, boolean isLoading) {
		super();
		this.id = id;
		this.isLoading = isLoading;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isLoading() {
		return isLoading;
	}

	public void setLoading(boolean isLoading) {
		this.isLoading = isLoading;
	}

}
