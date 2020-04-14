package com.packagename.myapp.model.base;

import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.simple.JSONObject;

public class Review {
	
	public static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	private int paperID;
	private String version;
	private String reviewerID;
	private Date reviewDate;
	private String filePath;
	private String comment;
	
	public Review(int paperID, String version, String reviewerID, Date reviewDate,
			String filePath, String comment) {
		
		this.paperID = paperID;
		setVersion(version);
		setReviewerID(reviewerID);
		setReviewDate(reviewDate);
		setFilePath(filePath);
		setComment(comment);
	}
	
	public Review(Review copy) {
		this(copy.paperID, copy.version, copy.reviewerID, copy.reviewDate, copy.filePath,
				copy.comment);
	}

	/**
	 * Constructs from given JSONObject.
	 * 
	 * @param obj
	 */
	public Review(JSONObject obj) {
		this(((Long) obj.get("PaperID")).intValue(), (String) obj.get("Version"),
				(String) obj.get("ReviewerID"),
				obj.get("ReviewDate") == null ? null :
					dateFormat.parse((String) obj.get("ReviewDate"), new ParsePosition(0)),
				(String) obj.get("FilePath"), (String) obj.get("Comment"));
	}

	public int getPaperID() {
		return paperID;
	}

	public void setPaperID(int paperID) {
		this.paperID = paperID;
	}

	public String getVersion() {
		return version == null ? null : new String(version);
	}

	public void setVersion(String version) {
		this.version = version == null ? null : new String(version);
	}

	public String getReviewerID() {
		return version == null ? null : new String(version);
	}

	public void setReviewerID(String reviewerID) {
		this.reviewerID = version == null ? null : new String(version);
	}

	public Date getReviewDate() {
		return reviewDate == null ? null : (Date) reviewDate.clone();
	}

	public void setReviewDate(Date reviewDate) {
		this.reviewDate = reviewDate == null ? null : (Date) reviewDate.clone();
	}

	public String getFilePath() {
		return filePath == null ? null : new String(filePath);
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath == null ? null : new String(filePath);
	}

	public String getComment() {
		return comment == null ? null : new String(comment);
	}

	public void setComment(String comment) {
		this.comment = comment == null ? null : new String(comment);
	}
	
	public static String formatOrNull(Date date) {
		return date == null ? null : dateFormat.format(date);
	}

	/**
	 * Creates a JSONObject representation of this instance
	 * 
	 * @return JSONObject representation
	 */
	public JSONObject jsonObject() {
		JSONObject o = new JSONObject();
		
		o.put("PaperID", getPaperID());
		o.put("Version", getVersion());
		o.put("ReviewerID", getReviewerID());
		o.put("ReviewDate", formatOrNull(getReviewDate()));
		o.put("FilePath", getFilePath());
		o.put("Comment", getComment());
		
		return o;
	}
	
	@Override
	public String toString() {
		return "{" + paperID + "," + version + "," + reviewerID
				 + "," + dateFormat.format(reviewDate)
				 + "," + filePath + "," + comment + "}";
	}	
}
