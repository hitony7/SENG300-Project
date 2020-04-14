package com.packagename.myapp.control;

import java.util.Collection;
import java.util.HashMap;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;

import com.packagename.myapp.model.base.EditorJournal;
import com.packagename.myapp.model.base.Paper;
import com.packagename.myapp.model.base.Submission;
import com.packagename.myapp.model.base.User;
import com.packagename.myapp.model.composite.PaperEntry;
import com.packagename.myapp.model.composite.SubmissionHistory;

public class EditorPageController {

	private HashMap<String,User> userData;
	private HashMap<Integer,Paper> paperData;
	private HashMap<Pair<Integer,String>,Submission> submissionData;
	private HashMap<String,EditorJournal> editorJournalData;
	
	private String userID;

	public EditorPageController(HashMap<String,User> userData,
			HashMap<Integer,Paper> paperData,
			HashMap<Pair<Integer,String>,Submission> submissionData,
			HashMap<String,EditorJournal> editorJournalData, String userID) {
		
		this.userData = userData;
		this.paperData = paperData;
		this.submissionData = submissionData;
		this.editorJournalData = editorJournalData;
		
		this.userID = userID;
	}

	// Query Methods
	public Collection<PaperEntry> getActivePapers() {
		return paperData.values().stream()
				.filter(paper -> userID.equals(paper.getEditorID()))
				.map(paper -> {
					Submission lastSub = getLastSubmission(paper);
					
					User researcher = userData.get(paper.getResearcherID());
					
					return new PaperEntry(paper, lastSub, researcher);
				}).collect(Collectors.toSet());
	}
	
	public Collection<PaperEntry> getJournalHistory() {
		return paperData.values().stream()
				.filter(paper -> paper.getJournal()
						.equals(editorJournalData.get(userID).getJName()))
				.map(paper -> {
					Submission lastSub = getLastSubmission(paper);
					
					User researcher = userData.get(paper.getResearcherID());
					
					return new PaperEntry(paper, lastSub, researcher);
				}).collect(Collectors.toSet());
	}
	
	public Submission getLastSubmission(Paper paper) {
		return submissionData.values().stream()
				.filter(sub -> sub.getPaperID() == paper.getPaperID())
				.max((a,b) -> a.getSubmissionDate().compareTo(b.getSubmissionDate()))
				.get();
	}
	
	public Collection<SubmissionHistory> getSubmissionHistory(PaperEntry paperEntry) {
		return submissionData.values().stream()
				.filter(sub -> sub.getPaperID() == paperEntry.getPaper().getPaperID())
				.map(sub -> {
					User editor = userData.get(paperEntry.getPaper().getEditorID());
					
					return new SubmissionHistory(paperEntry.getPaper(), sub, editor);
				}).collect(Collectors.toSet());
	}
	
}
