package com.packagename.myapp.model;

import org.json.simple.JSONObject;

public class NominatedReviewer {
	
	private int paperID;
	private String reviewerID;
	
	public NominatedReviewer(int paperID, String reviewerID) {
		this.paperID = paperID;
		setReviewerID(reviewerID);
	}
	
	public NominatedReviewer(NominatedReviewer copy) {
		this(copy.paperID, copy.reviewerID);
	}
	
	/**
	 * Constructs from given JSONObject.
	 * 
	 * @param obj
	 */
	public NominatedReviewer(JSONObject obj) {
		this(((Long) obj.get("PaperID")).intValue(),
				(String) obj.get("ReviewerID"));
	}

	public int getPaperID() {
		return paperID;
	}

	public void setPaperID(int paperID) {
		this.paperID = paperID;
	}

	public String getReviewerID() {
		return reviewerID == null ? null : new String(reviewerID);
	}

	public void setReviewerID(String reviewerID) {
		this.reviewerID = reviewerID == null ? null : new String(reviewerID);
	}
	
	/**
	 * Creates a JSONObject representation of this instance
	 * 
	 * @return JSONObject representation
	 */
	public JSONObject jsonObject() {
		JSONObject o = new JSONObject();

		o.put("PaperID", getPaperID());
		o.put("ReviewerID", getReviewerID());
		
		return o;
	}	

}
