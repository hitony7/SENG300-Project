package com.packagename.myapp.model;

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
	
	
	public Submission(int paperID) {
		this(paperID, null, null, null, null, null);
	}
	
	public Submission(int paperID, String version, Date submissionDate, String filePath, 
			String researcherMessage, SubStatus status) {
		this(paperID, version, submissionDate, null, null, null, filePath, researcherMessage, null, status);
	}
	
	public Submission(int paperID, String version, Date submissionDate, Date decisionDate, 
			Date resubmissionDeadline, Date reviewDeadline, String filePath, 
			String researcherMessage, String editorComment, SubStatus status) {
		this.paperID = paperID;
		setVersion(version);
		setSubmissionDate(submissionDate);
		setDecisionDate(decisionDate);
		setResubmissionDeadline(resubmissionDeadline);
		setReviewDeadline(reviewDeadline);
		setFilePath(filePath);
		setResearcherMessage(researcherMessage);
		setEditorComment(editorComment);
		setStatus(status);
	}

	public Submission(Submission copy) {
		this(copy.paperID, copy.version, copy.submissionDate, copy.decisionDate,
				copy.resubmissionDeadline, copy.reviewDeadline, copy.filePath,
				copy.researcherMessage, copy.editorComment, copy.status);
	}

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
	
	public Date getSubmissionDate() {
		return submissionDate == null ? null : (Date) submissionDate.clone();
	}
	
	public void setSubmissionDate(Date submissionDate) {
		this.submissionDate = submissionDate == null ? null 
				: (Date) submissionDate.clone();
	}

	public Date getDecisionDate() {
		return decisionDate == null ? null : (Date) decisionDate.clone();
	}
	
	public void setDecisionDate(Date decisionDate) {
		this.decisionDate = decisionDate == null ? null : (Date) decisionDate.clone();
	}
	
	public Date getResubmissionDeadline() {
		return resubmissionDeadline == null ? null : (Date) resubmissionDeadline.clone();
	}
	
	public void setResubmissionDeadline(Date resubmissionDeadline) {
		this.resubmissionDeadline = resubmissionDeadline == null ? null 
				: (Date) resubmissionDeadline.clone();
	}
	
	public Date getReviewDeadline() {
		return reviewDeadline == null ? null : (Date) reviewDeadline.clone();
	}
	
	public void setReviewDeadline(Date reviewDeadline) {
		this.reviewDeadline = reviewDeadline == null ? null 
				: (Date) reviewDeadline.clone();
	}
	
	public String getFilePath() {
		return filePath == null ? null : new String(filePath);
	}
	
	public void setFilePath(String filePath) {
		this.filePath = filePath == null ? null : new String(filePath);
	}
	
	public String getResearcherMessage() {
		return researcherMessage == null ? null : new String(researcherMessage);
	}
	
	public void setResearcherMessage(String researcherMessage) {
		this.researcherMessage = researcherMessage == null ? null 
				: new String(researcherMessage);
	}
	
	public String getEditorComment() {
		return editorComment == null ? null : new String(editorComment);
	}
	
	public void setEditorComment(String editorComment) {
		this.editorComment = editorComment == null ? null : new String(editorComment);
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
	public JSONObject jsonObject() {
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
	
	@Override
	public String toString() {
		return "{" + paperID + "," + version + "," + dateFormat.format(submissionDate)
				 + "," + dateFormat.format(decisionDate)
				 + "," + dateFormat.format(resubmissionDeadline)
				 + "," + dateFormat.format(reviewDeadline) + "," + filePath
				 + "," + researcherMessage + "," + editorComment + "," + status + "}";
	}

}
