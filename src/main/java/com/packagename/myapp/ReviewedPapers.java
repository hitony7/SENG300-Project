package com.packagename.myapp;

public class ReviewedPapers {

	String paper;
	String journal;
	String date;
	String desc;
	String reviewer;
	String status;
	
	public ReviewedPapers(String paper, String journal, String date, String desc, String reviewer, String status) {
		super();
		this.paper = paper;
		this.journal = journal;
		this.date = date;
		this.desc = desc;
		this.reviewer = reviewer;
		this.status = status;
	}

	public String getPaper() {
		return paper;
	}

	public void setPaper(String paper) {
		this.paper = paper;
	}

	public String getJournal() {
		return journal;
	}

	public void setJournal(String journal) {
		this.journal = journal;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getReviewer() {
		return reviewer;
	}

	public void setReviewer(String reviewer) {
		this.reviewer = reviewer;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
}
