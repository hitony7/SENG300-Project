package com.packagename.myapp.model.composite;

import com.packagename.myapp.model.base.Paper;
import com.packagename.myapp.model.base.Submission;
import com.packagename.myapp.model.base.User;

public class ActivePaper {

	private Paper paper;
	private Submission submission;
	private User researcher;
	
	public ActivePaper(Paper paper, Submission submission, User researcher) {
		super();
		this.paper = paper;
		this.submission = submission;
		this.researcher = researcher;
	}

	public String getPaperTitle() {
		return paper.getTitle();
	}
	
	public String getVersion() {
		return submission.getVersion();
	}
	
	public String getResearcher() {
		return researcher.getName();
	}
	
	public String getStatus() {
		return submission.toString();
	}
	
	
	
	
	
}
