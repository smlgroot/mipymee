package com.kalimeradev.mipymee.client;

import java.io.Serializable;

@SuppressWarnings("serial")
public class LoginResult implements Serializable{
	String user;
	String url;
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
}
