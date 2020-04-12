package com.packagename.myapp;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.packagename.myapp.model.Paper;
import com.packagename.myapp.model.Submission;
import com.packagename.myapp.model.User;

public class SubmissionHistory {
	private Submission submission;
	private User editor;
	
	public SubmissionHistory(Submission submission, User editor) {
		super();
		this.submission = submission;
		this.editor = editor;
	}
	
	public String getVersion() {
		return submission.getVersion();
	}
	
	public String getSubDate() {
		Date temp = submission.getSubmissionDate();
		String date = dateToString(temp);
		return date;
	}
	
	public String getResubDeadline() {
		Date temp = submission.getResubmissionDeadline();
		String date = dateToString(temp);
		return date;
	}
	
	public String getReviewDeadline() {
		Date temp = submission.getReviewDeadline();
		String date = dateToString(temp);
		return date;
	}
	
	public String getEditor() {
		return editor.getName();
	}
	
	/*
	 * Takes a date as a paramater, and turns it into a string.
	 */
	public String dateToString(Date temp) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
		String str = dateFormat.format(temp);
		return str;
	}
	
}
