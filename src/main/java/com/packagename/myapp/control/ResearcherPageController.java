package com.packagename.myapp.control;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.tuple.Pair;

import com.packagename.myapp.model.Journal;
import com.packagename.myapp.model.Paper;
import com.packagename.myapp.model.PersonalJournalHistory;
import com.packagename.myapp.model.PersonalSubmissionHistory;
import com.packagename.myapp.model.Submission;
import com.packagename.myapp.model.User;

public class ResearcherPageController {

	private HashMap<String,User> userData;
	private HashMap<Integer,Paper> paperData;
	private HashMap<Pair<Integer,String>,Submission> submissionData;
	private HashMap<String,Journal> journalData;
	
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
		/*return journalData.values().stream()
				.map(journal -> {
					Stream<Paper> papers = paperData.values().stream()
							.filter(paper -> paper.getResearcherID().equals(userID)
									&& paper.getJournal().equals(journal.getJName()));
					
					Collection<PersonalSubmissionHistory> personalSubmissionHistory = 
							submissionData.values().stream().map(sub -> {
								Paper matchedPaper = papers
										.filter(paper -> sub.getPaperID() == paper.getPaperID())
										.findAny().orElse(null);
								if (matchedPaper != null) {
									User editor = userData.values().stream()
											.filter(user -> user.getUserID().equals(matchedPaper.getEditorID()))
											.findAny().orElse(null);
									return new PersonalSubmissionHistory(matchedPaper, sub, editor);
								} else {
									return null;
								}
							}).filter(sub -> sub != null)
							.collect(Collectors.toSet());
					
					return new PersonalJournalHistory(journal, (int) papers.count(), 
							personalSubmissionHistory.size(), personalSubmissionHistory);
				}).collect(Collectors.toSet());*/
		return journalData.values().stream()
				.map(journal -> {
					Collection<Paper> papers = paperData.values().stream()
							.filter(paper -> paper.getResearcherID().equals(userID)
									&& paper.getJournal().equals(journal.getJName()))
							.collect(Collectors.toSet());
					
					Collection<PersonalSubmissionHistory> personalSubmissionHistory = 
							submissionData.values().stream()
							.filter(sub -> {
								Paper matchedPaper = paperData.get(sub.getPaperID());
								
								return papers.contains(matchedPaper);
							})
							.map(sub -> {
								Paper matchedPaper = paperData.get(sub.getPaperID());
								
								User editor = userData.get(matchedPaper.getEditorID());
								
								return new PersonalSubmissionHistory(matchedPaper, sub, editor);
							}).collect(Collectors.toSet());
					
					return new PersonalJournalHistory(journal, (int) papers.size(),
							personalSubmissionHistory.size(), personalSubmissionHistory);
				}).collect(Collectors.toSet());
	}
}
