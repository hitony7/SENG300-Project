package com.packagename.myapp;

public class ReviewerSubmissions {
	String journal;
	String paper;
	String version;
	String researcher;
	String reviewDeadline;
	String editor;
	
	public ReviewerSubmissions(String journal, String paper, String version, String researcher, String reviewDeadline,
			String editor) {
		super();
		this.journal = journal;
		this.paper = paper;
		this.version = version;
		this.researcher = researcher;
		this.reviewDeadline = reviewDeadline;
		this.editor = editor;
	}
	// getter and setter for journal, paper, version, researcher, review deadline, editor
	public String getJournal() {
		return journal;
	}
	public void setJournal(String journal) {
		this.journal = journal;
	}
	public String getPaper() {
		return paper;
	}
	public void setPaper(String paper) {
		this.paper = paper;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getResearcher() {
		return researcher;
	}
	public void setResearcher(String researcher) {
		this.researcher = researcher;
	}
	public String getReviewDeadline() {
		return reviewDeadline;
	}
	public void setReviewDeadline(String reviewDeadline) {
		this.reviewDeadline = reviewDeadline;
	}
	public String getEditor() {
		return editor;
	}
	public void setEditor(String editor) {
		this.editor = editor;
	}
	
	

	
	
}
