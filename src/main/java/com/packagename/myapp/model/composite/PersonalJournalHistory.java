package com.packagename.myapp.model.composite;

import java.util.Collection;

import com.packagename.myapp.model.base.Journal;

public class PersonalJournalHistory {

	//variables created for use in the class
	private Journal journal;
	private int papersSubmitted;
	private int submissionsMade;
	
	private Collection<SubmissionHistory> submissionHistory;
	
	public PersonalJournalHistory(Journal journal, int papersSubmitted,
			int submissionsMade, Collection<SubmissionHistory> submissionHistory) {
		
		this.journal = journal;
		this.papersSubmitted = papersSubmitted;
		this.submissionsMade = submissionsMade;
		this.submissionHistory = submissionHistory;
	}

	//getter for journal name
	public String getJName() {
		return new String(journal.getJName());
	}

	//getter for field
	public String getField() {
		return new String(journal.getField());
	}

	//getter for paper submitted
	public int getPapersSubmitted() {
		return papersSubmitted;
	}

	//getter for submission made
	public int getSubmissionsMade() {
		return submissionsMade;
	}

	//getter for submission history
	public Collection<SubmissionHistory> getSubmissionHistory() {
		return submissionHistory;
	}
}
