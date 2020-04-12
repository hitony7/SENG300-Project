package com.packagename.myapp.model;

import org.json.simple.JSONObject;

public class User {

	private int userID;
	private String password;
	private String name;
	private String email;
	private String field;
	private boolean researcherFlag;
	private boolean reviewerFlag;
	private boolean editorFlag;
	private boolean adminFlag;
	
	public User(int userID, String password, String name, String email, String field,
			boolean researcherFlag, boolean reviewerFlag, boolean editorFlag,
			boolean adminFlag) {
		this.userID = userID;
		setPassword(password);
		setName(name);
		setEmail(email);
		setField(field);
		this.researcherFlag = researcherFlag;
		this.reviewerFlag = reviewerFlag;
		this.editorFlag = editorFlag;
		this.adminFlag = adminFlag;		
	}
	
	public User(User copy) {
		this(copy.userID, copy.password, copy.name, copy.email, copy.field,
				copy.researcherFlag, copy.reviewerFlag, copy.editorFlag,
				copy.adminFlag);
	}
	
	/**
	 * Constructs from given JSONObject.
	 * 
	 * @param obj
	 */
	public User(JSONObject obj) {
		this(((Long) obj.get("UserID")).intValue(),
				(String) obj.get("Password"),
				(String) obj.get("Name"),
				(String) obj.get("Email"),
				(String) obj.get("Field"),
				(boolean) obj.get("ResearcherFlag"),
				(boolean) obj.get("ReviewerFlag"),
				(boolean) obj.get("EditorFlag"),
				(boolean) obj.get("AdminFlag"));
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public String getPassword() {
		return password == null ? null : new String(password);
	}

	public void setPassword(String password) {
		this.password = password == null ? null : new String(password);
	}

	public String getName() {
		return name == null ? null : new String(name);
	}

	public void setName(String name) {
		this.name = name == null ? null : new String(name);
	}

	public String getEmail() {
		return email == null ? null : new String(email);
	}

	public void setEmail(String email) {
		this.email = email == null ? null : new String(email);
	}

	public String getField() {
		return field == null ? null : new String(field);
	}

	public void setField(String field) {
		this.field = field == null ? null : new String(field);
	}

	public boolean getResearcherFlag() {
		return researcherFlag;
	}

	public void setResearcherFlag(boolean researcherFlag) {
		this.researcherFlag = researcherFlag;
	}

	public boolean getReviewerFlag() {
		return reviewerFlag;
	}

	public void setReviewerFlag(boolean reviewerFlag) {
		this.reviewerFlag = reviewerFlag;
	}

	public boolean getEditorFlag() {
		return editorFlag;
	}

	public void setEditorFlag(boolean editorFlag) {
		this.editorFlag = editorFlag;
	}

	public boolean getAdminFlag() {
		return adminFlag;
	}

	public void setAdminFlag(boolean adminFlag) {
		this.adminFlag = adminFlag;
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
		o.put("ResearcherFlag", getResearcherFlag());
		o.put("ReviewerFlag", getReviewerFlag());
		o.put("EditorFlag", getEditorFlag());
		o.put("AdminFlag", getAdminFlag());
		
		return o;
	}	
	
}
