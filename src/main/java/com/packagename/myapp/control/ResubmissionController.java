package com.packagename.myapp.control;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import com.packagename.myapp.model.base.JsonModel;
import com.packagename.myapp.model.base.Paper;
import com.packagename.myapp.model.base.Submission;
import com.packagename.myapp.model.base.Submission.SubStatus;
import com.packagename.myapp.model.base.User;
import com.vaadin.flow.component.notification.Notification;

/**
 * 'Controller object' for paper resubmission page.
 * 
 * Used to help bind form data of researcher's resubmission page to model data,
 * which is then used to write to files using JsonModel.
 * 
 * @author SeanP1225
 *
 */
public class ResubmissionController {
	
	private HashMap<String,User> userData;
	private HashMap<Integer,Paper> paperData;
	private HashMap<Pair<Integer,String>,Submission> submissionData;
	
	private Paper paper;
	private Submission submission;
	private InputStream inputStream;
	private String filename;
	private User researcher;
	
	
	public ResubmissionController(HashMap<String, User> userData,
			HashMap<Integer, Paper> paperData,
			HashMap<Pair<Integer, String>, Submission> submissionData,
			String researcherID) {
		
		this.userData = userData;
		this.paperData = paperData;
		this.submissionData = submissionData;
		
		researcher = getUserByID(researcherID);
	}
	
	public Paper getPaper() {
		return new Paper(paper);
	}
	
	public void setPaper(Paper paper) {
		this.paper = new Paper(paper);
		this.submission = new Submission(paper.getPaperID(), null, new Date(), null, null, SubStatus.PN_AC);
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	public String getTitle() {
		return paper.getTitle();
	}
	
	public void setTitle(String title) {
		paper.setTitle(title);
	}
	
	public String getVersion() {
		return submission.getVersion();
	}
	
	public void setVersion(String version) {
		submission.setVersion(version);
	}
	
	public String getResearcherMessage() {
		return submission.getResearcherMessage();
	}
	
	public void setResearcherMessage(String message) {
		submission.setResearcherMessage(message);;
	}
	
	public void setStatus(SubStatus status) {
		submission.setStatus(status);
	}
	
	// Query Methods
	public User getUserByID(String userID) {
		return userData.values().stream()
				.filter(user -> user.getUserID().equals(userID))
				.findFirst().get();
	}
	
	public Collection<Paper> getPapersByUserResearcher() {
		return paperData.values().stream()
				.filter(paper -> paper.getResearcherID().equals(researcher.getUserID()))
				.collect(Collectors.toSet());
	}
	
	public boolean checkVersionExists(String sub) {
		return submissionData.containsKey(ImmutablePair.of(paper.getPaperID(), sub));
	}
	
	public Submission getLastSubmission() {
		return submissionData.values().stream()
				.filter(sub -> sub.getPaperID() == submission.getPaperID())
				.max((a, b) -> a.getSubmissionDate().compareTo(b.getSubmissionDate()))
				.get();
	}
	
	public void createResubmission() throws IOException {
		
	    // get journal directory
		String journalPath = "data\\journals\\" + paper.getJournal();

		// create file in journal directory
		String filePath = journalPath + "\\" + filename;
		submission.setFilePath(filePath);
		
	    File f = new File(filePath);
	    if (f.exists()) {
	    	throw new IOException("File already exists.");
	    }
	    f.createNewFile();
	
	    // copy uploaded content to f
	    FileUtils.copyInputStreamToFile(inputStream, f);
	    Notification.show("File upload successful: " + f.getName());
	    
	    // add to data maps and write to data files
	    paperData.put(paper.getPaperID(), paper);
	    submissionData.put(Pair.of(new Integer(submission.getPaperID()), 
	    		submission.getVersion()), submission);
	    
	    JsonModel.setPaperData(paperData);
	    JsonModel.setSubmissionData(submissionData);
	}
	
}
