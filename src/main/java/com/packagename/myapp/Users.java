package com.packagename.myapp;

public class Users {
	String name;
	String password;
	String userType;
	String email;
	
	public Users(String name, String password, String userType, String email) {
		super();
		this.name = name;
		this.password = password;
		this.userType = userType;
		this.email = email;
	}
	
	//getter and setter for name, password, usertype, email
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
		this.password = password;
	}

	public String getUsertype() {
		return userType;
	}

	public void setUsertype(String userType) {
		this.userType = userType;
	}

	public String getEmail(){
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
