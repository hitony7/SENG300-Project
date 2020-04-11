package com.packagename.myapp;

public class ReviewedPapers {

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

	public String getReviewDeadline() {
		return reviewDeadline;
	}

	public void setReviewDeadline(String reviewDeadline) {
		this.reviewDeadline = reviewDeadline;
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

	public String getEditor() {
		return editor;
	}

	public void setEditor(String editor) {
		this.editor = editor;
	}

	

	
	
	
	
}
