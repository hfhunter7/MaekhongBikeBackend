package com.gaopai.maekhongbikebackend.bean;

import java.io.Serializable;

public class AuthorizationBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String appToken;
	
	private String tokenKey;

	private String emailToken;

	public AuthorizationBean() {
		// TODO Auto-generated constructor stub
	}

	public AuthorizationBean(String appToken, String tokenKey) {
		super();
		this.appToken = appToken;
		this.tokenKey = tokenKey;
	}

	public String getAppToken() {
		return appToken;
	}

	public void setAppToken(String appToken) {
		this.appToken = appToken;
	}

	public String getTokenKey() {
		return tokenKey;
	}

	public void setTokenKey(String tokenKey) {
		this.tokenKey = tokenKey;
	}

	public String getEmailToken() {
		return emailToken;
	}

	public void setEmailToken(String emailToken) {
		this.emailToken = emailToken;
	}
}
