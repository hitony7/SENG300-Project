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
	//Hashmap variables for holding user, paper, submission
	private HashMap<String,User> userData;
	private HashMap<Integer,Paper> paperData;
	private HashMap<Pair<Integer,String>,Submission> submissionData;

	//Varibles create for paper, submission, inputstream, filename, researcher data
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

	//getter and setter for paper data
	public Paper getPaper() {
		return new Paper(paper);
	}
	
	public void setPaper(Paper paper) {
		this.paper = new Paper(paper);
		this.submission = new Submission(paper.getPaperID(), null, new Date(), null, null, SubStatus.PN_AC);
	}

	//setter for the input stream
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	//setter for the filename
	public void setFilename(String filename) {
		this.filename = filename;
	}

	//getter and setter for the title
	public String getTitle() {
		return paper.getTitle();
	}
	
	public void setTitle(String title) {
		paper.setTitle(title);
	}

	//getter and setter for the version
	public String getVersion() {
		return submission.getVersion();
	}
	
	public void setVersion(String version) {
		submission.setVersion(version);
	}

	//getter and setter for the researcher message
	public String getResearcherMessage() {
		return submission.getResearcherMessage();
	}
	
	public void setResearcherMessage(String message) {
		submission.setResearcherMessage(message);;
	}

	//setter for the status
	public void setStatus(SubStatus status) {
		submission.setStatus(status);
	}
	
	// Query Methods
	public User getUserByID(String userID) {
		return userData.values().stream()
				.filter(user -> user.getUserID().equals(userID))
				.findFirst().get();
	}

	//getter for the paper sorted by researcher who submitted it
	public Collection<Paper> getPapersByUserResearcher() {
		return paperData.values().stream()
				.filter(paper -> paper.getResearcherID().equals(researcher.getUserID()))
				.collect(Collectors.toSet());
	}

	//Method check if that version exists
	public boolean checkVersionExists(String sub) {
		return submissionData.containsKey(ImmutablePair.of(paper.getPaperID(), sub));
	}

	//getter for the last submission
	public Submission getLastSubmission() {
		return submissionData.values().stream()
				.filter(sub -> sub.getPaperID() == submission.getPaperID())
				.max((a, b) -> a.getSubmissionDate().compareTo(b.getSubmissionDate()))
				.get();
	}

	//method to create a resubmission
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
