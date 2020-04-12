package com.packagename.myapp.control;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.tuple.Pair;

import com.packagename.myapp.model.Journal;
import com.packagename.myapp.model.JournalEditor;
import com.packagename.myapp.model.JsonModel;
import com.packagename.myapp.model.Paper;
import com.packagename.myapp.model.Submission;
import com.packagename.myapp.model.Submission.SubStatus;
import com.packagename.myapp.model.User;
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
	
	private HashMap<String, User> userData;
	private HashMap<Integer, Paper> paperData;
	private HashMap<Pair<Integer, String>, Submission> submissionData;
	private HashMap<String, Journal> journalData;
	private HashMap<String, JournalEditor> journalEditorData;
	
	private Paper paper;
	private Submission submission;
	private InputStream inputStream;
	private String filename;
	//private User researcher;
	private User editor;
	private Collection<User> reviewers;
	

	public NewSubmissionController() {
		this(null);
	}
	
	public NewSubmissionController(String researcherID) {
		try {
			userData = JsonModel.getUserData();
			paperData = JsonModel.getPaperData();
			submissionData = JsonModel.getSubmissionData();
			journalData = JsonModel.getJournalData();
			journalEditorData = JsonModel.getJournalEditorData();
		} catch (IOException e1) {
			e1.printStackTrace();
			userData = new HashMap<>();
			paperData = new HashMap<>();
			submissionData = new HashMap<>();
			journalData = new HashMap<>();
			journalEditorData = new HashMap<>();
		}
		int paperID = getNumberOfPapers();
		
		paper = new Paper(paperID, null, researcherID, null);
		submission = new Submission(paperID, "0.0.0", new Date(), null, null, SubStatus.PN_CL);
		// researcher = new User(researcherID)
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	public User getEditor() {
		return editor;
	}
	
	//public Collection<User> 
	
	public void setEditor(User editor) {
		this.editor = editor;
		this.paper.setEditorID(editor.getUserID());
	}

	public String getResearcherID() {
		return paper.getResearcherID();
	}
	
	public void setResearcherID(String researcherID) {
		this.paper.setResearcherID(researcherID);
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
	
	public void setStatus(SubStatus status) {
		submission.setStatus(status);
	}
	
	// Query Methods
	public int getNumberOfPapers() {
		return paperData.size();
	}
	
	public Collection<Journal> getAllJournals() {
		return journalData.values();
	}
	
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
	
	public Collection<User> getAllReviewers() {
		return userData.values().stream()
				.filter(user
						-> user.getUserType().equals("Reviewer"))
				.collect(Collectors.toSet());
	}

	public void newResearcherSubmission()
					throws IOException {
	
	    // create journal directory if it does not exist
		String journalPath = "data\\journals\\" + paper.getJournal();
		File journalDir = new File(journalPath);
		
		if (!journalDir.exists()) {
			journalDir.mkdirs();
		}
		
		// create file in journal directory
		String filePath = journalPath + filename;
		submission.setFilePath(filePath);
		
	    File f = new File(journalPath + filename);
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
