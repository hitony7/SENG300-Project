package com.packagename.myapp.control;

import java.util.ArrayList;
import java.util.HashMap;

import com.packagename.myapp.Users;
import com.packagename.myapp.model.User;

public class ManageUserController {

	private Users user;
	
	public String getUserName() {
		return user.getName();
	}
	
	public void setUserName(String userName) {
		user.setName(userName);
	}
	
	/*public ArrayList<User> getAllUsers(HashMap<Integer,User> userData) {
		return List. userData.keySet()
	}*/
	
}
