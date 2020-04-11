package com.packagename.myapp.control;

import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.simple.JSONObject;

/**
 * Model object representing the tuples of the Submission relation in RM and in submission.json
 * 
 * @author SeanP1225
 *
 */
public class Submission {
	
	public static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	public static enum SubStatus {
		ACC("Accepted"),
		REJ("Rejected"),
		MJ_RV("Requires Major Revision"),
		MN_RV("Requires Minor Revision"),
		PN_CL("Pending Collection"),
		PN_ED("Pending Editor Assignment"),
		PN_AC("Pending Editor Action"),
		RVWS("Requires Review");
		
		private String fullMessage;
		
		SubStatus(String fullMessage) {
			this.fullMessage = fullMessage;
		}
		
		public String toString() {
			return fullMessage;
		}
	}
	
	private int paperID;
	private String version;
	private Date submissionDate;
	private Date decisionDate;
	private Date resubmissionDeadline;
	private Date reviewDeadline;
	private String filePath;
	private String researcherMessage;
	private String editorComment;
	private SubStatus status;

	/**
	 * Constructs from given JSONObject.
	 * 
	 * @param obj
	 */
	public Submission(JSONObject obj) {
		this(((Long) obj.get("PaperID")).intValue(), (String) obj.get("Version"),
				obj.get("SubmissionDate") == null ? null :
					dateFormat.parse((String) obj.get("SubmissionDate"), new ParsePosition(0)),
				obj.get("DecisionDate") == null ? null :
					dateFormat.parse((String) obj.get("DecisionDate"), new ParsePosition(0)),
				obj.get("ResubmissionDeadline") == null ? null :
					dateFormat.parse((String) obj.get("ResubmissionDeadline"), new ParsePosition(0)),
				obj.get("ReviewDeadline") == null ? null :
					dateFormat.parse((String) obj.get("ReviewDeadline"), new ParsePosition(0)),
				(String) obj.get("FilePath"), (String) obj.get("ResearcherMessage"),
				(String) obj.get("EditorComment"),
				SubStatus.valueOf((String) obj.get("Status")));
	}
	
	public Submission(int paperID, String version, Date submissionDate, String filePath, 
			String researcherMessage, SubStatus status) {
		this(paperID, version, submissionDate, null, null, null, filePath, researcherMessage, null, status);
	}
	
	public Submission(int paperID, String version, Date submissionDate, Date decisionDate, 
			Date resubmissionDeadline, Date reviewDeadline, String filePath, 
			String researcherMessage, String editorComment, SubStatus status) {
		this.paperID = paperID;
		this.version = version;
		this.submissionDate = submissionDate;
		setDecisionDate(decisionDate);
		setResubmissionDeadline(resubmissionDeadline);
		setReviewDeadline(reviewDeadline);
		setFilePath(filePath);
		this.researcherMessage = researcherMessage;
		setEditorComment(editorComment);
		setStatus(status);
	}

	
	public int getPaperID() {
		return paperID;
	}
	
	protected void setPaperID(int paperID) {
		this.paperID = paperID;
	}
	
	public String getVersion() {
		return version;
	}
	
	protected void setVersion(String version) {
		this.version = version;
	}
	
	public Date getSubmissionDate() {
		return submissionDate;
	}
	
	protected void setSubmissionDate(Date submissionDate) {
		this.submissionDate = submissionDate;
	}

	public Date getDecisionDate() {
		return decisionDate;
	}
	
	public void setDecisionDate(Date decisionDate) {
		this.decisionDate = decisionDate;
	}
	
	public Date getResubmissionDeadline() {
		return resubmissionDeadline;
	}
	
	public void setResubmissionDeadline(Date resubmissionDeadline) {
		this.resubmissionDeadline = resubmissionDeadline;
	}
	
	public Date getReviewDeadline() {
		return reviewDeadline;
	}
	
	public void setReviewDeadline(Date reviewDeadline) {
		this.reviewDeadline = reviewDeadline;
	}
	
	public String getFilePath() {
		return filePath;
	}
	
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	public String getResearcherMessage() {
		return researcherMessage;
	}
	
	protected void setResearcherMessage(String researcherMessage) {
		this.researcherMessage = researcherMessage;
	}
	
	public String getEditorComment() {
		return editorComment;
	}
	
	public void setEditorComment(String editorComment) {
		this.editorComment = editorComment;
	}
	
	public SubStatus getStatus() {
		return status;
	}
	
	public void setStatus(SubStatus status) {
		this.status = status;
	}
	
	public String formatOrNull(Date date) {
		return date == null ? null : dateFormat.format(date);
	}

	/**
	 * Creates a JSONObject representation of this instance
	 * 
	 * @return JSONObject representation
	 */
	public JSONObject getJSONObject() {
		JSONObject o = new JSONObject();
		
		o.put("PaperID", getPaperID());
		o.put("Version", getVersion());
		o.put("SubmissionDate", formatOrNull(getSubmissionDate()));
		o.put("DecisionDate", formatOrNull(getDecisionDate()));
		o.put("ResubmissionDeadline", formatOrNull(getResubmissionDeadline()));
		o.put("ReviewDeadline", formatOrNull(getReviewDeadline()));
		o.put("FilePath", getFilePath());
		o.put("ResearcherMessage", getResearcherMessage());
		o.put("EditorComment", getEditorComment());
		o.put("Status", getStatus().name());
		
		return o;
	}

}
