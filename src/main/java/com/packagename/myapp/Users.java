package com.packagename.myapp;

public class Users {
	String name;
	String password;
	String userType;
	
	
	public Users(String name, String email, String userType) {
		super();
		this.name = name;
		this.password = email;
		this.userType = userType;
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String email) {
		this.password = email;
	}


	public String getUsertype() {
		return userType;
	}


	public void setUsertype(String userType) {
		this.userType = userType;
	}
	
	
	
	
	
}
