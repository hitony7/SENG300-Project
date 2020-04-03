package com.packagename.myapp;

public class ReviewerSubmissions {
	String name;
	String submitDate;
	
	public ReviewerSubmissions(String name, String submitDate) {
		super();
		this.name = name;
		this.submitDate = submitDate;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSubmitDate() {
		return submitDate;
	}
	public void setSubmitDate(String submitDate) {
		this.submitDate = submitDate;
	}
}
