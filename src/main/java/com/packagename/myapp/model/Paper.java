package com.packagename.myapp.model;

import org.json.simple.JSONObject;

/**
 * Model object representing the tuples of the Paper relation in RM and paper.json
 * 
 * @author SeanP1225
 *
 */
public class Paper {
	
	private int paperID;
	private String title;
	private int researcherID;
	private String journal;
	// Following instance variabels are wrapped in Integer to allow null values.
	private Integer editorID;
	private Integer collectionYear;
	private Integer collectionHalfYear;
	
	
	public Paper(int paperID, int researcherID) {
		this(paperID, null, researcherID, null);
	}
	
	public Paper(int paperID, String title, int researcherID, String journal) {
		this(paperID, title, researcherID, journal, null, null, null);
	}
	
	public Paper(int paperID, String title, int researcherID, String journal, 
			Integer editorID, Integer collectionYear, Integer collectionHalfYear) {
		this.paperID = paperID;
		setTitle(title);
		this.researcherID = researcherID;
		setJournal(journal);
		setEditorID(editorID);
		setCollectionYear(collectionYear);
		setCollectionHalfYear(collectionHalfYear);
		
	}

	public Paper(Paper copy) {
		this(copy.paperID, copy.title, copy.researcherID, copy.journal, copy.editorID,
				copy.collectionYear, copy.collectionHalfYear);
	}
	
	/**
	 * Constructs from given JSONObject.
	 * 
	 * @param obj
	 */
	public Paper(JSONObject obj) {
		this(((Long) obj.get("PaperID")).intValue(), 
				(String) obj.get("Title"), 
				((Long) obj.get("ResearcherID")).intValue(), 
				(String) obj.get("Journal"), 
				((Long) obj.get("EditorID")).intValue(),
				((Long) obj.get("CollectionYear")).intValue(),
				((Long) obj.get("CollectionHalfYear")).intValue());
	}

	public int getPaperID() {
		return paperID;
	}
	
	public void setPaperID(int paperID) {
		this.paperID = paperID;
	}

	public String getTitle() {
		return title == null ? null : new String(title);
	}

	public void setTitle(String title) {
		this.title = title == null ? null : new String(title);
	}

	public int getResearcherID() {
		return researcherID;
	}
	
	public void setResearcherID(int researcherID) {
		this.researcherID = researcherID;
	}

	public String getJournal() {
		return journal == null ? null : new String(journal);
	}
	
	public void setJournal(String journal) {
		this.journal = journal == null ? null : new String(journal);
	}

	public Integer getEditorID() {
		return editorID == null ? null : new Integer(editorID);
	}
	
	public void setEditorID(Integer editorID) {
		this.editorID = editorID == null ? null : new Integer(editorID);
	}

	public void setEditorID(int editorID) {
		this.editorID = new Integer(editorID);
	}

	public Integer getCollectionYear() {
		return collectionYear == null ? null : new Integer(collectionYear);
	}

	public void setCollectionYear(Integer collectionYear) {
		this.collectionYear = collectionYear == null ? null : new Integer(collectionYear);
	}

	public void setCollectionYear(int collectionYear) {
		this.collectionYear = new Integer(collectionYear);
	}

	public Integer getCollectionHalfYear() {
		return collectionYear == null ? null : new Integer(collectionHalfYear);
	}

	public void setCollectionHalfYear(Integer collectionHalfYear) {
		this.collectionHalfYear = collectionHalfYear == null ? null : new Integer(collectionHalfYear);
	}

	public void setCollectionHalfYear(int collectionHalfYear) {
		this.collectionHalfYear = new Integer(collectionHalfYear);
	}
	
	/**
	 * Creates a JSONObject representation of this instance
	 * 
	 * @return JSONObject representation
	 */
	public JSONObject jsonObject() {
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
