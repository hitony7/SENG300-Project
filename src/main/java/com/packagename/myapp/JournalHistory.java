package com.packagename.myapp;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.packagename.myapp.model.Paper;
import com.packagename.myapp.model.Submission;
import com.packagename.myapp.model.User;

public class JournalHistory {

	private Paper paper;
	private Submission submission;
	private User researcher;
	
	public JournalHistory(Paper paper, Submission submission, User researcher) {
		super();
		this.paper = paper;
		this.submission = submission;
	}
	
	public String getPaperTitle() {
		return paper.getTitle();
	}
	
	public String getResearcher() {
		return researcher.getName();
	}
	
	public String getYear() {
		int temp = paper.getCollectionHalfYear();
		return Integer.toString(temp);
	}
	
	public String getDecisionDate() {
		Date temp = submission.getDecisionDate();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
		String date = dateFormat.format(temp);
		return date;
	}
	
	public String getStatus() {
		return submission.toString();
	}
	
}