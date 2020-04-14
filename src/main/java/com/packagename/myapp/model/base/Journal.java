package com.packagename.myapp.model.base;

import org.json.simple.JSONObject;

public class Journal {

	private String jName;
	private String field;
	
	public Journal(String jName, String field) {
		setJName(jName);
		setField(field);
	}
	
	public Journal(Journal copy) {
		this(copy.jName, copy.field);
	}
	
	/**
	 * Constructs from given JSONObject.
	 * 
	 * @param obj
	 */
	public Journal(JSONObject obj) {
		this((String) obj.get("JName"),
				(String) obj.get("Field"));
	}

	public String getJName() {
		return jName == null ? null : new String(jName);
	}

	public void setJName(String jName) {
		this.jName = jName == null ? null : new String(jName);
	}

	public String getField() {
		return field == null ? null : new String(field);
	}

	public void setField(String field) {
		this.field = field == null ? null : new String(field);
	}
	
	/**
	 * Creates a JSONObject representation of this instance
	 * 
	 * @return JSONObject representation
	 */
	public JSONObject jsonObject() {
		JSONObject o = new JSONObject();
		
		o.put("JName", getJName());
		o.put("Field", getField());
		
		return o;
	}
	
	@Override
	public String toString() {
		return "{" + jName + "," + field + "}";
	}
	
}
