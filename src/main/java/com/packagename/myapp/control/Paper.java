package com.packagename.myapp.control;

import org.json.simple.JSONObject;

public class Paper {
	private final int paperID;
	private String title;
	private final int researcherID;
	private final String journal;
	// Following instance variabels are wrapped in Integer to allow null values.
	private Integer editorID;
	private Integer collectionYear;
	private Integer collectionHalfYear;
	
	/**
	 * Constructs from given JSONObject.
	 * 
	 * @param obj
	 */
	public Paper(JSONObject obj) {
		this((int) obj.get("PaperID"), 
				(String) obj.get("Title"), 
				(int) obj.get("ResearcherID"), 
				(String) obj.get("Journal"), 
				new Integer((int) obj.get("EditorID")), 
				new Integer((int) obj.get("CollectionYear")), 
				new Integer((int) obj.get("CollectionHalfYear")));
	}
	
	public Paper(int paperID, String title, int researcherID, String journal) {
		this(paperID, title, researcherID, journal, null, null, null);
	}
	
	public Paper(int paperID, String title, int researcherID, String journal, 
			Integer editorID, Integer collectionYear, Integer collectionHalfYear) {
		this.paperID = paperID;
		setTitle(title);
		this.researcherID = researcherID;
		this.journal = journal;
		this.editorID = editorID;
	  	this.collectionYear = collectionYear;
		this.collectionHalfYear = collectionHalfYear;
		
	}

	public Paper(Paper copy) {
		this.paperID = copy.paperID;
		this.title = copy.title;
		this.researcherID = copy.researcherID;
		this.journal = copy.journal;
		this.editorID = copy.editorID;
		this.collectionYear = copy.collectionYear;
		this.collectionHalfYear = copy.collectionHalfYear;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getEditorID() {
		return editorID.intValue();
	}
	
	public void setEditorID(Integer editorID) {
		this.editorID = editorID;
	}

	public void setEditorID(int editorID) {
		setEditorID(new Integer(editorID));
	}

	public int getCollectionYear() {
		return collectionYear.intValue();
	}

	public void setCollectionYear(int collectionYear) {
		this.collectionYear = new Integer(collectionYear);
	}

	public int getCollectionHalfYear() {
		return collectionHalfYear.intValue();
	}

	public void setCollectionHalfYear(int collectionHalfYear) {
		this.collectionHalfYear = new Integer(collectionHalfYear);
	}

	public int getPaperID() {
		return paperID;
	}

	public int getResearcherID() {
		return researcherID;
	}

	public String getJournal() {
		return journal;
	}
	
	/**
	 * Creates a JSONObject representation of this instance
	 * 
	 * @return JSONObject representation
	 */
	public JSONObject getJSONObject() {
		JSONObject o = new JSONObject();
		
		o.put("PaperID", getPaperID());
		o.put("Title", getTitle());
		o.put("ResearcherID", getResearcherID());
		o.put("Journal", getJournal());
		o.put("EditorID", getEditorID());
		o.put("CollectionYear", getCollectionYear());
		o.put("CollectionHalfYear", getCollectionHalfYear());
		
		return o;
	}
	
}
