package com.packagename.myapp.model.composite;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.packagename.myapp.model.base.Paper;
import com.packagename.myapp.model.base.Submission;
import com.packagename.myapp.model.base.User;

public class PaperEntry {

	private Paper paper;
	private Submission lastSubmission;
	private User researcher;
	
	public PaperEntry(Paper paper, Submission lastSubmission, User researcher) {
		this.paper = paper;
		this.lastSubmission = lastSubmission;
		this.researcher = researcher;
	}
	
	public Paper getPaper() {
		return new Paper(paper);
	}
	
	public String getPaperTitle() {
		return paper.getTitle();
	}
	
	public String getResearcherName() {
		return researcher.getName();
	}
	
	public String getVersion() {
		return lastSubmission.getVersion();
	}
	
	public String getCollectionYearHalfYear() {
		Integer year = paper.getCollectionYear();
		Integer halfyear = paper.getCollectionHalfYear();
		
		return year == null ? "Not yet collected."
				: year + "-" + halfyear;
	}
	
	public String getLastDecisionDate() {
		Date decisionDate = lastSubmission.getDecisionDate();
		
		return decisionDate == null ? "Not yet decided."
				: Submission.dateFormat.format(decisionDate);
	}
	
	public String getStatus() {
		return lastSubmission.getStatus().toString();
	}
}
