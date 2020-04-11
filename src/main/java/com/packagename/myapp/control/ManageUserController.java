package com.packagename.myapp.control;

import com.packagename.myapp.Users;

public class ManageUserController {

	private Users user;
	
	public String getUserName() {
		return user.getName();
	}
	
	public void setUserName(String userName) {
		user.setName(userName);
	}
	
}
