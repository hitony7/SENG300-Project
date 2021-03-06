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

import com.packagename.myapp.ReviewerListController;
import com.packagename.myapp.model.base.EditorJournal;
import com.packagename.myapp.model.base.Journal;
import com.packagename.myapp.model.base.JsonModel;
import com.packagename.myapp.model.base.NominatedReviewer;
import com.packagename.myapp.model.base.Paper;
import com.packagename.myapp.model.base.Submission;
import com.packagename.myapp.model.base.Submission.SubStatus;
import com.packagename.myapp.model.base.User;
import com.vaadin.flow.component.notification.Notification;

/**
 * 'Controller object' for new paper submission page.
 * 
 * Used to help bind form data of researcher's new submission page to model data,
 * which is then used to write to files using JsonModel.
 * 
 * @author SeanP1225
 *
 */
public class NewSubmissionController {

	//Hashmap variables for holding user, paper, submission, journal, editor journal, and reviewer data
	private HashMap<String,User> userData;
	private HashMap<Integer,Paper> paperData;
	private HashMap<Pair<Integer,String>,Submission> submissionData;
	private HashMap<String,Journal> journalData;
	private HashMap<String,EditorJournal> journalEditorData;
	private HashMap<Pair<Integer,String>,NominatedReviewer> nominatedReviewerData;

	//Variable created to hold values
	private Paper paper;
	private Submission submission;
	private InputStream inputStream;
	private String filename;
	//private User researcher;
	private User editor;

	public NewSubmissionController(HashMap<String, User> userData,
			HashMap<Integer, Paper> paperData,
			HashMap<Pair<Integer, String>, Submission> submissionData,
			HashMap<String, Journal> journalData,
			HashMap<String, EditorJournal> journalEditorData,
			HashMap<Pair<Integer,String>,NominatedReviewer> nominatedReviewerData, 
			String researcherID) {
		
		this.userData = userData;
		this.paperData = paperData;
		this.submissionData = submissionData;
		this.journalData = journalData;
		this.journalEditorData = journalEditorData;
		this.nominatedReviewerData = nominatedReviewerData;
    
		int paperID = getNumberOfPapers();
		
		paper = new Paper(paperID, null, researcherID, null);
		submission = new Submission(paperID, "0.0.0", new Date(), null, null, SubStatus.PN_CL);
		// researcher = new User(researcherID)
	}

	//setter for the input stream
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	//setter for the file name
	public void setFilename(String filename) {
		this.filename = filename;
	}

	//getter and setter for editor
	public User getEditor() {
		return editor;
	}
	
	public void setEditor(User editor) {
		if (editor != null) {
			this.editor = editor;
			this.paper.setEditorID(editor.getUserID());
		}
	}

	//getter and setter for researcher ID
	public String getResearcherID() {
		return paper.getResearcherID();
	}
	
	public void setResearcherID(String researcherID) {
		this.paper.setResearcherID(researcherID);
	}

	//getter and setter for title
	public String getTitle() {
		return paper.getTitle();
	}
	
	public void setTitle(String title) {
		paper.setTitle(title);
	}

	//getter and setter for journal
	public String getJournal() {
		return paper.getJournal();
	}
	
	public void setJournal(String journal) {
		paper.setJournal(journal);;
	}

	//getter and setter for Researcher message
	public String getResearcherMessage() {
		return submission.getResearcherMessage();
	}
	
	public void setResearcherMessage(String message) {
		submission.setResearcherMessage(message);;
	}

	// setter for status
	public void setStatus(SubStatus status) {
		submission.setStatus(status);
	}
	
	// Query Methods
	//getter for number of papers
	public int getNumberOfPapers() {
		return paperData.size();
	}

	//getter for all journals
	public Collection<Journal> getAllJournals() {
		return journalData.values();
	}

	//getter for editor by journal assigned
	public Collection<User> getAllEditorsByJournal(Journal journal) {
		Collection<String> editorIDsByJournal = journalEditorData.values().stream()
				.filter(journalEditor
						-> journalEditor.getJName().equals(journal.getJName()))
				.map(journalEditor
						-> journalEditor.getEditorID())
				.collect(Collectors.toSet());
		
		return userData.values().stream()
				.filter(editor
						-> editorIDsByJournal.contains(editor.getUserID()))
				.collect(Collectors.toSet());
	}

	//getter for all of reviewer user
	public Collection<User> getAllReviewers() {
		return userData.values().stream()
				.filter(user
						-> user.getUserType() != null && user.getUserType().equals("Reviewer"))
				.collect(Collectors.toSet());
	}

	//Method for creating a new submission
	public void newResearcherSubmission(ReviewerListController revController) throws IOException {
	
	    // create journal directory if it does not exist
		String journalPath = "data\\journals\\" + paper.getJournal();
		File journalDir = new File(journalPath);
		
		if (!journalDir.exists()) {
			journalDir.mkdirs();
		}

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
	    nominatedReviewerData.putAll(revController.getAllReviewers().stream()
	    		.collect(Collectors.toMap(rev -> ImmutablePair.of(paper.getPaperID(), rev.getUserID()), 
	    				rev -> new NominatedReviewer(paper.getPaperID(), rev.getUserID()))));
	    
	    JsonModel.setPaperData(paperData);
	    JsonModel.setSubmissionData(submissionData);
	    JsonModel.setNominatedReviewerData(nominatedReviewerData);
	}
	
}
