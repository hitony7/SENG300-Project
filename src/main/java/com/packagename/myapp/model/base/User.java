package com.packagename.myapp.model.base;

import org.json.simple.JSONObject;

public class User {

	//variables created for user id, password, name, email, field, user type
	private String userID;
	private String password;
	private String name;
	private String email;
	private String field;
	private String userType;
	
	public User(String userID, String password, String name, String email, String field,
			String userType) {
		setUserID(userID);
		setPassword(password);
		setName(name);
		setEmail(email);
		setField(field);
		setUserType(userType);	
	}
	
	public User(User copy) {
		this(copy.userID, copy.password, copy.name, copy.email, copy.field,
				copy.userType);
	}
	
	/**
	 * Constructs from given JSONObject.
	 * 
	 * @param obj
	 */
	public User(JSONObject obj) {
		this((String) obj.get("UserID"),
				(String) obj.get("Password"),
				(String) obj.get("Name"),
				(String) obj.get("Email"),
				(String) obj.get("Field"),
				(String) obj.get("UserType"));
	}

	//getter and setter for user id
	public String getUserID() {
		return userID == null ? null : new String(userID);
	}

	public void setUserID(String userID) {
		this.userID = userID == null ? null : new String(userID);
	}

	//getter and setter for password
	public String getPassword() {
		return password == null ? null : new String(password);
	}

	public void setPassword(String password) {
		this.password = password == null ? null : new String(password);
	}

	//getter and setter for name
	public String getName() {
		return name == null ? null : new String(name);
	}

	public void setName(String name) {
		this.name = name == null ? null : new String(name);
	}

	//getter and setter for email
	public String getEmail() {
		return email == null ? null : new String(email);
	}

	public void setEmail(String email) {
		this.email = email == null ? null : new String(email);
	}

	//getter and setter for field
	public String getField() {
		return field == null ? null : new String(field);
	}

	public void setField(String field) {
		this.field = field == null ? null : new String(field);
	}

	//getter and setter for user type
	public String getUserType() {
		return userType == null ? null : new String(userType);
	}
	
	public void setUserType(String userType) {
		this.userType = userType == null ? null : new String(userType);
	}
	
	/**
	 * Creates a JSONObject representation of this instance
	 * 
	 * @return JSONObject representation
	 */
	public JSONObject jsonObject() {
		JSONObject o = new JSONObject();

		o.put("UserID", getUserID());
		o.put("Password", getPassword());
		o.put("Name", getName());
		o.put("Email", getEmail());
		o.put("Field", getField());
		o.put("UserType", getUserType());
		
		return o;
	}

	//method to check user password
	public boolean checkUserPass(String username, String password) {
		if (username.equals(getUserID()) && (password.equals(getPassword()))){
			return true;

		}
		return false;
	}
	
	@Override
	public String toString() {
		return "{" + userID + "," + password + "," + name + "," + email + "," + field
				 + "," + userType + "}";
	}
}
