package com.packagename.myapp.control;

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
				obj.get("EditorID") == null ? null
						: new Integer(((Long) obj.get("EditorID")).intValue()),
				obj.get("CollectionYear") == null ? null
						: new Integer(((Long) obj.get("CollectionYear")).intValue()),
				obj.get("CollectionHalfYear") == null ? null
						: new Integer(((Long) obj.get("CollectionHalfYear")).intValue()));
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

	public int getPaperID() {
		return paperID;
	}
	
	protected void setPaperID(int paperID) {
		this.paperID = paperID;
	}

	public int getResearcherID() {
		return researcherID;
	}
	
	protected void setResearcherID(int researcherID) {
		this.researcherID = researcherID;
	}

	public String getJournal() {
		return journal;
	}
	
	protected void setJournal(String journal) {
		this.journal = journal;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getEditorID() {
		return editorID;
	}
	
	public void setEditorID(Integer editorID) {
		this.editorID = editorID;
	}

	public void setEditorID(int editorID) {
		setEditorID(new Integer(editorID));
	}

	public Integer getCollectionYear() {
		return collectionYear;
	}

	public void setCollectionYear(int collectionYear) {
		this.collectionYear = new Integer(collectionYear);
	}

	public Integer getCollectionHalfYear() {
		return collectionHalfYear;
	}

	public void setCollectionHalfYear(int collectionHalfYear) {
		this.collectionHalfYear = new Integer(collectionHalfYear);
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
