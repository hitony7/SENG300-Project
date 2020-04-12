package com.packagename.myapp.model;

import org.json.simple.JSONObject;

public class EditorJournal {

	private String editorID;
	private String jName;
	
	public EditorJournal(String editorID, String jName) {
		setEditorID(editorID);
		setJName(jName);
	}
	
	public EditorJournal(EditorJournal copy) {
		this(copy.editorID, copy.jName);
	}
	
	/**
	 * Constructs from given JSONObject.
	 * 
	 * @param obj
	 */
	public EditorJournal(JSONObject obj) {
		this((String) obj.get("EditorID"), 
				(String) obj.get("JName"));
	}

	public String getEditorID() {
		return editorID == null ? null : new String(editorID);
	}

	public void setEditorID(String editorID) {
		this.editorID = editorID == null ? null : new String(editorID);
	}

	public String getJName() {
		return jName == null ? null : new String(jName);
	}

	public void setJName(String jName) {
		this.jName = jName == null ? null : new String(jName);
	}
	
	/**
	 * Creates a JSONObject representation of this instance
	 * 
	 * @return JSONObject representation
	 */
	public JSONObject jsonObject() {
		JSONObject o = new JSONObject();

		o.put("EditorID", getEditorID());
		o.put("JName", getJName());
		
		return o;
	}
	
	@Override
	public String toString() {
		return "{" + editorID + "," + jName + "}";
	}
}
