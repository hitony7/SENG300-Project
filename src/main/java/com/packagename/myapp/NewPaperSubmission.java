package com.packagename.myapp;

import com.packagename.myapp.control.Paper;
import com.packagename.myapp.control.Submission;

public class NewPaperSubmission {
	private Paper paper;
	private Submission submission;
	
	//temporary, to be replaced by actual User objects (corresponding to User table in RM)
	private int researcherID;
	private String editorEmail;

	
	public Paper getPaper() {
		return paper;
	}
	
	public Submission getSubmission() {
		return submission;
	}
	
	public int getResearcherID() {
		return researcherID;
	}
	
	public void setResearcherID(int researcherID) {
		this.researcherID = researcherID;
	}
	
	public String getEditorEmail() {
		return editorEmail;
	}
	
	public void setEditorEmail(String editorEmail) {
		this.editorEmail = editorEmail;
	}
	
	public String getJournal() {
		return paper.getJournal();
	}
	
	public void setJournal(String journal) {
		Paper p = getPaper();
		paper = new Paper(p.getPaperID(),
				p.getTitle(),
				p.getResearcherID(),
				journal,
				p.getEditorID(),
				p.getCollectionYear(),
				p.getCollectionHalfYear());
	}
	
	public String getResearcherMessage() {
		return submission.getResearcherMessage();
	}
	
	public void setResearcherMessage(String message) {
		Submission s = getSubmission();
		submission = new Submission(s.getPaperID(),
				s.getVersion(),
				s.getSubmissionDate(),
				s.getDecisionDate(),
				s.getResubmissionDeadline(),
				s.getReviewDeadline(),
				s.getFilePath(),
				message,
				s.getEditorComment(),
				s.getStatus());
	}
}
