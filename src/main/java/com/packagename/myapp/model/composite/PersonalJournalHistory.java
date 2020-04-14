package com.packagename.myapp.model.composite;

import java.util.Collection;

import com.packagename.myapp.model.base.Journal;

public class PersonalJournalHistory {

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
	
	public String getJName() {
		return new String(journal.getJName());
	}
	
	public String getField() {
		return new String(journal.getField());
	}

	public int getPapersSubmitted() {
		return papersSubmitted;
	}

	public int getSubmissionsMade() {
		return submissionsMade;
	}

	public Collection<SubmissionHistory> getSubmissionHistory() {
		return submissionHistory;
	}
}
