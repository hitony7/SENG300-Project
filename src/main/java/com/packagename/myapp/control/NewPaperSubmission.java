package com.packagename.myapp.control;

import java.io.InputStream;
import java.util.Date;

import com.packagename.myapp.control.Paper;
import com.packagename.myapp.control.Submission;
import com.packagename.myapp.control.Submission.SubStatus;

/**
 * Business object combining model objects into one.
 * 
 * Used to help bind form data of researcher's new submission page to model data,
 * which is then used to write to files.
 * 
 * @author SeanP1225
 *
 */
public class NewPaperSubmission {
	
	private Paper paper;
	private Submission submission;
	private InputStream inputStream;
	private String filename;
	
	
	//temporary, to be replaced by actual User objects (corresponding to User table in RM)
	private int researcherID;
	private String editorEmail;

	public NewPaperSubmission() {
		this(-1, -1);
	}
	
	public NewPaperSubmission(int paperID, int researcherID) {
		paper = new Paper(paperID, null, researcherID, null);
		submission = new Submission(paperID, "0.0.0", new Date(), null, null, SubStatus.PN_CL);
	}
	
	public Paper getPaper() {
		return paper;
	}
	
	public Submission getSubmission() {
		return submission;
	}
	
	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public int getResearcherID() {
		return researcherID;
	}
	
	public void setResearcherID(int researcherID) {
		this.researcherID = researcherID;
		this.paper.setResearcherID(researcherID);
	}
	
	public String getEditorEmail() {
		return editorEmail;
	}
	
	public void setEditorEmail(String editorEmail) {
		this.editorEmail = editorEmail;
		//this.paper.setEditorID(editorID);
	}
	
	public String getTitle() {
		return paper.getTitle();
	}
	
	public void setTitle(String title) {
		paper.setTitle(title);
	}
	
	public String getJournal() {
		return paper.getJournal();
	}
	
	public void setJournal(String journal) {
		paper.setJournal(journal);;
	}
	
	public String getResearcherMessage() {
		return submission.getResearcherMessage();
	}
	
	public void setResearcherMessage(String message) {
		submission.setResearcherMessage(message);;
	}
	
	public String getFilePath() {
		return submission.getFilePath();
	}
	
	public void setFilePath(String filePath) {
		submission.setFilePath(filePath);
	}
	
	public void setStatus(SubStatus status) {
		submission.setStatus(status);
	}
	
}
