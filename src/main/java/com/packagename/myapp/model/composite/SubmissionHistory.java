package com.packagename.myapp.model.composite;

import java.util.Date;

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
	
	public Submission getSubmission() {
		return new Submission(submission);
	}
	
	public String getTitle() {
		return new String(paper.getTitle());
	}


	
	public String getVersion() {
		return new String(submission.getVersion());
	}
	
	public String getSubmissionDate() {
		return Submission.formatOrNull(submission.getSubmissionDate());
	}
	
	public String getResubmissionDeadline() {
		Date deadline = submission.getResubmissionDeadline();
		
		return deadline == null ? "Not yet set." 
				: Submission.dateFormat.format(deadline);
	}
	
	public String getReviewDeadline() {
		Date deadline = submission.getReviewDeadline();
		
		return deadline == null ? "Not yet set." 
				: Submission.dateFormat.format(deadline);
	}
	
	public String getEditorEmail() {
		return editor == null ? "Not assigned." : editor.getEmail();
	}
	
	public String getStatus() {
		return submission.getStatus().toString();
	}
}
