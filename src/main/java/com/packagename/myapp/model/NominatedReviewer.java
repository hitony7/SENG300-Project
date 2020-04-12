package com.packagename.myapp.model;

import org.json.simple.JSONObject;

public class NominatedReviewer {
	
	private int paperID;
	private int reviewerID;
	
	public NominatedReviewer(int paperID, int reviewerID) {
		this.paperID = paperID;
		this.reviewerID = reviewerID;
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
				((Long) obj.get("ReviewerID")).intValue());
	}

	public int getPaperID() {
		return paperID;
	}

	public void setPaperID(int paperID) {
		this.paperID = paperID;
	}

	public int getReviewerID() {
		return reviewerID;
	}

	public void setReviewerID(int reviewerID) {
		this.reviewerID = reviewerID;
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
