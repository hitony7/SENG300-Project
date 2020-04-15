package com.packagename.myapp;

public class ReviewedPapers {
	//create variable for paper, journal, review deadline, version, researcher, and editor
	String paper;
	String journal;
	String reviewDeadline;
	String version;
	String researcher;
	String editor;
	
	public ReviewedPapers(String journal, String paper, String version, String researcher, String reviewDeadline, String editor) {
		super();
		this.paper = paper;
		this.journal = journal;
		this.reviewDeadline = reviewDeadline;
		this.version = version;
		this.researcher = researcher;
		this.editor = editor;
	}

	//getter and setter for paper data
	public String getPaper() {
		return paper;
	}

	public void setPaper(String paper) {
		this.paper = paper;
	}

	//getter and setter journal data
	public String getJournal() {
		return journal;
	}

	public void setJournal(String journal) {
		this.journal = journal;
	}

	//getter and setter for review deadline
	public String getReviewDeadline() {
		return reviewDeadline;
	}

	public void setReviewDeadline(String reviewDeadline) {
		this.reviewDeadline = reviewDeadline;
	}

	//getter and setter for version deadline
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	//getter and setter for researcher data
	public String getResearcher() {
		return researcher;
	}

	public void setResearcher(String researcher) {
		this.researcher = researcher;
	}

	//getter and setter for editor data
	public String getEditor() {
		return editor;
	}

	public void setEditor(String editor) {
		this.editor = editor;
	}

	

	
	
	
	
}
