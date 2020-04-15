package com.packagename.myapp.control;

import java.util.Collection;
import java.util.HashMap;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;

import com.packagename.myapp.model.base.Journal;
import com.packagename.myapp.model.base.Paper;
import com.packagename.myapp.model.base.Submission;
import com.packagename.myapp.model.base.User;
import com.packagename.myapp.model.composite.PersonalJournalHistory;
import com.packagename.myapp.model.composite.SubmissionHistory;

public class ResearcherPageController {
	//Hashmap variables for holding user, paper, submission, journal
	private HashMap<String,User> userData;
	private HashMap<Integer,Paper> paperData;
	private HashMap<Pair<Integer,String>,Submission> submissionData;
	private HashMap<String,Journal> journalData;

	//Variable for userID
	private String userID;

	public ResearcherPageController(HashMap<String,User> userData,
			HashMap<Integer,Paper> paperData,
			HashMap<Pair<Integer,String>,Submission> submissionData,
			HashMap<String,Journal> journalData, String userID) {
		
		this.userData = userData;
		this.paperData = paperData;
		this.submissionData = submissionData;
		this.journalData = journalData;
		
		this.userID = userID;
	}
	
	
	// Query Methods	
	public Collection<PersonalJournalHistory> getPersonalJournalHistory() {
		return journalData.values().stream()
				.map(journal -> {
					Collection<Paper> papers = paperData.values().stream()
							.filter(paper -> paper.getResearcherID().equals(userID)
									&& paper.getJournal().equals(journal.getJName()))
							.collect(Collectors.toSet());
					
					Collection<SubmissionHistory> personalSubmissionHistory = 
							submissionData.values().stream()
							.filter(sub -> {
								Paper matchedPaper = paperData.get(sub.getPaperID());
								
								return papers.contains(matchedPaper);
							})
							.map(sub -> {
								Paper matchedPaper = paperData.get(sub.getPaperID());
								
								User editor = userData.get(matchedPaper.getEditorID());
								
								return new SubmissionHistory(matchedPaper, sub, editor);
							}).collect(Collectors.toSet());
					
					return new PersonalJournalHistory(journal, (int) papers.size(),
							personalSubmissionHistory.size(), personalSubmissionHistory);
				}).collect(Collectors.toSet());
	}
}
