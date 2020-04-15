package com.packagename.myapp.model.composite;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.packagename.myapp.model.base.Paper;
import com.packagename.myapp.model.base.Submission;
import com.packagename.myapp.model.base.User;

public class PaperEntry {

	//created variables for paper, submission and researcher
	private Paper paper;
	private Submission lastSubmission;
	private User researcher;
	
	public PaperEntry(Paper paper, Submission lastSubmission, User researcher) {
		this.paper = paper;
		this.lastSubmission = lastSubmission;
		this.researcher = researcher;
	}

	//getter for paper
	public Paper getPaper() {
		return new Paper(paper);
	}

	//getter for paper title
	public String getPaperTitle() {
		return paper.getTitle();
	}

	//getter for researcher name
	public String getResearcherName() {
		return researcher.getName();
	}

	//getter for version
	public String getVersion() {
		return lastSubmission.getVersion();
	}

	//getter for collection year half year
	public String getCollectionYearHalfYear() {
		Integer year = paper.getCollectionYear();
		Integer halfyear = paper.getCollectionHalfYear();
		
		return year == null ? "Not yet collected."
				: year + "-" + halfyear;
	}

	//getter for last decision date
	public String getLastDecisionDate() {
		Date decisionDate = lastSubmission.getDecisionDate();
		
		return decisionDate == null ? "Not yet decided."
				: Submission.dateFormat.format(decisionDate);
	}

	//getter for status
	public String getStatus() {
		return lastSubmission.getStatus().toString();
	}
}
