package com.packagename.myapp.model.composite;

import com.packagename.myapp.model.base.Paper;
import com.packagename.myapp.model.base.Submission;
import com.packagename.myapp.model.base.User;

public class SubmissionHistory {

	private Paper paper;
	private Submission submission;
	private User editor;
	
	public SubmissionHistory(Paper paper, Submission submission, User editor) {
		this.paper = paper;
		this.submission = submission;
		this.editor = editor;
	}
	
	public String getTitle() {
		return new String(paper.getTitle());
	}
	
	public String getVersion() {
		return new String(submission.getVersion());
	}
	
	public String getSubmissionDate() {
		return submission.formatOrNull(submission.getSubmissionDate());
	}
	
	public String getResubmissionDeadline() {
		return submission.formatOrNull(submission.getResubmissionDeadline());
	}
	
	public String getReviewDeadline() {
		return submission.formatOrNull(submission.getReviewDeadline());
	}
	
	public String getEditorEmail() {
		return editor == null ? "Not assigned." : editor.getEmail();
	}
	
	public String getStatus() {
		return submission.getStatus().toString();
	}
}
