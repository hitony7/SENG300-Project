package com.packagename.myapp.model;

import org.json.simple.JSONObject;

public class JournalEditor {

	private String jName;
	private String editorID;
	
	public JournalEditor(String jName, String editorID) {
		setJName(jName);
		setEditorID(editorID);
	}
	
	public JournalEditor(JournalEditor copy) {
		this(copy.jName, copy.editorID);
	}
	
	/**
	 * Constructs from given JSONObject.
	 * 
	 * @param obj
	 */
	public JournalEditor(JSONObject obj) {
		this((String) obj.get("JName"), 
				(String) obj.get("EditorID"));
	}

	public String getJName() {
		return jName == null ? null : new String(jName);
	}

	public void setJName(String jName) {
		this.jName = jName == null ? null : new String(jName);
	}

	public String getEditorID() {
		return editorID == null ? null : new String(editorID);
	}

	public void setEditorID(String editorID) {
		this.editorID = editorID == null ? null : new String(editorID);
	}
	
	/**
	 * Creates a JSONObject representation of this instance
	 * 
	 * @return JSONObject representation
	 */
	public JSONObject jsonObject() {
		JSONObject o = new JSONObject();

		o.put("JName", getJName());
		o.put("EditorID", getEditorID());
		
		return o;
	}
}
