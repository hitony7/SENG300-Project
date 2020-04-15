package com.packagename.myapp.model.composite;

import java.util.Date;

import com.packagename.myapp.model.base.Paper;
import com.packagename.myapp.model.base.Submission;
import com.packagename.myapp.model.base.User;

public class SubmissionHistory {
	//variable created for paper, submission, editor
	private Paper paper;
	private Submission submission;
	private User editor;
	
	public SubmissionHistory(Paper paper, Submission submission, User editor) {
		this.paper = paper;
		this.submission = submission;
		this.editor = editor;
	}

	//getter for submission
	public Submission getSubmission() {
		return new Submission(submission);
	}

	// getter for title
	public String getTitle() {
		return new String(paper.getTitle());
	}


	//getter for version of paper
	public String getVersion() {
		return new String(submission.getVersion());
	}

	// getter for submission date of the paper
	public String getSubmissionDate() {
		return Submission.formatOrNull(submission.getSubmissionDate());
	}

	//getter for resubmission deadline date
	public String getResubmissionDeadline() {
		Date deadline = submission.getResubmissionDeadline();
		
		return deadline == null ? "Not yet set." 
				: Submission.dateFormat.format(deadline);
	}

	//getter for review deadline of the paper
	public String getReviewDeadline() {
		Date deadline = submission.getReviewDeadline();
		
		return deadline == null ? "Not yet set." 
				: Submission.dateFormat.format(deadline);
	}

	//getter editor email
	public String getEditorEmail() {
		return editor == null ? "Not assigned." : editor.getEmail();
	}

	//getter status
	public String getStatus() {
		return submission.getStatus().toString();
	}
}
