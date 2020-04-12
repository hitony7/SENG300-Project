package com.packagename.myapp.control;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.tuple.Pair;

import com.packagename.myapp.model.JsonModel;
import com.packagename.myapp.model.Paper;
import com.packagename.myapp.model.Submission;
import com.packagename.myapp.model.Submission.SubStatus;
import com.packagename.myapp.model.User;
import com.vaadin.flow.component.notification.Notification;

/**
 * 'Controller object' for new paper submission page.
 *
 * Used to help bind form data of researcher's new submission page to model data,
 * which is then used to write to files using JsonModel.
 * @Ref Sean
 * @author Hitony7
 *
 */
public class ManageUserController {

    private User user;
    private Paper paper;
    private Submission submission;
    private InputStream inputStream;
    private String filename;

    // temporary, to be replaced by actual User objects (corresponding to User table in RM)
    //private String editorEmail;
    private User researcher;
    private User editor;


    public ManageUserController() {
        this(-1, "");
    }

    public ManageUserController(int paperID, String researcherID) {
        paper = new Paper(paperID, null, researcherID, null);
        submission = new Submission(paperID, "0.0.0", new Date(), null, null, SubStatus.PN_CL);
        // researcher = new User(researcherID)
    }

    public static User validateLogin(HashMap<String, User> userList, String username, String password) {
        for (User i : userList.values()) {
            if(i.checkUserPass(username,password)){
                return i;
            }
        }
        return null;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getEditorEmail() {
        return editor == null ? null : editor.getEmail();
    }

    public void setEditorEmail(String editorEmail) {
        //this.editorEmail = editorEmail;
        //this.paper.setEditorID(editorID);
    }

    public String getResearcherID() {
        return paper.getResearcherID();
    }

    public void setResearcherID(String researcherID) {
        this.paper.setResearcherID(researcherID);
    }

    public String getTitle() {
        return paper.getTitle();
    }

    public void setTitle(String title) {
        paper.setTitle(title);
    }

    public String getJournal() {
        return paper.getJournal();
    }

    public void setJournal(String journal) {
        paper.setJournal(journal);;
    }

    public String getResearcherMessage() {
        return submission.getResearcherMessage();
    }

    public void setResearcherMessage(String message) {
        submission.setResearcherMessage(message);;
    }

    public void setStatus(SubStatus status) {
        submission.setStatus(status);
    }


    public static int getNumberOfPapers(HashMap<Integer,Paper> paperData) {
        return paperData.size();
    }

    public void newResearcherSubmission(HashMap<Integer,Paper> paperData,
                                        HashMap<Pair<Integer,String>,Submission> submissionData)
            throws IOException {

        // create journal directory if it does not exist
        String journalPath = "data\\journals\\" + paper.getJournal();
        File journalDir = new File(journalPath);

        if (!journalDir.exists()) {
            journalDir.mkdirs();
        }

        // create file in journal directory
        String filePath = journalPath + filename;
        submission.setFilePath(filePath);

        File f = new File(journalPath + filename);
        f.createNewFile();

        // copy uploaded content to f
        FileUtils.copyInputStreamToFile(inputStream, f);
        Notification.show("File upload successful: " + f.getName());

        // add to data maps and write to data files

        paperData.put(paper.getPaperID(), paper);
        submissionData.put(Pair.of(new Integer(submission.getPaperID()),
                submission.getVersion()), submission);

        JsonModel.setPaperData(paperData);
        JsonModel.setSubmissionData(submissionData);
    }

}
