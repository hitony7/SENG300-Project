package com.packagename.myapp;

import java.io.IOException;
import java.util.HashMap;

import org.apache.commons.lang3.tuple.Pair;

import com.packagename.myapp.control.ResubmissionController;
import com.packagename.myapp.model.base.JsonModel;
import com.packagename.myapp.model.base.Paper;
import com.packagename.myapp.model.base.Submission;
import com.packagename.myapp.model.base.User;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.validator.StringLengthValidator;
import com.vaadin.flow.router.Route;

@Route(value = "resubmit")
public class ResubmissionView extends VerticalLayout{

	//creates hashmap variable for user, paper, submission
	private HashMap<String,User> userData;
	private HashMap<Integer,Paper> paperData;
	private HashMap<Pair<Integer,String>,Submission> submissionData;
	
	ResubmissionController subController;

	private H1 heading = new H1("Create a Resubmission");
	private FormLayout submissionForm = new FormLayout();
	private Select<Paper> paperSelect = new Select<>();
	private TextField titleField = new TextField();
	private TextField versionField = new TextField();
	private TextArea messageField = new TextArea("Message to Editor");
	private Upload upload = new Upload(new MemoryBuffer());
	
	private Button submitButton = new Button("Submit");
	private Button backButton = new Button("Back");
	
	
	
	public ResubmissionView() {
		setMaxWidth("50em");
		
		Binder<ResubmissionController> subBinder = new Binder(ResubmissionController.class);

		try {
			userData = JsonModel.getUserData();
			paperData = JsonModel.getPaperData();
			submissionData = JsonModel.getSubmissionData();
		} catch (IOException e1) {
			e1.printStackTrace();
			userData = new HashMap<>();
			paperData = new HashMap<>();
			submissionData = new HashMap<>();
		}
		
		// placeholder value
		String researcherID = "admin";
		subController = new ResubmissionController(userData, paperData, submissionData,
				researcherID);
		
		// paper select
		paperSelect.setItems(subController.getPapersByUserResearcher());
		paperSelect.setItemLabelGenerator(Paper::getTitle);
		paperSelect.addValueChangeListener(e -> {
			if (e == null) {
				titleField.setEnabled(false);
				versionField.setEnabled(false);
				messageField.setEnabled(false);
				
			} else {
				Paper paper = e.getValue();
				subController.setPaper(paper);
				titleField.setEnabled(true);
				versionField.setEnabled(true);
				messageField.setEnabled(true);
				titleField.setValue(paper.getTitle());
				versionField.setValue(subController.getLastSubmission().getVersion());
			}
			upload.setReceiver(new MemoryBuffer());
		});
		
		subBinder.forField(paperSelect)
				.asRequired()
				.bind(ResubmissionController::getPaper, ResubmissionController::setPaper);
		
		
		// title field
		subBinder.forField(titleField)
				.asRequired("Title required.")
				.withValidator(new StringLengthValidator("Maximum 50 character.", 0, 50))
				.bind(ResubmissionController::getTitle, ResubmissionController::setTitle);
		
		
		// version field
		subBinder.forField(versionField)
				.asRequired("New version required.")
				.withValidator(sub -> !subController.checkVersionExists(sub), "Version already exists.")
				.bind(ResubmissionController::getVersion, ResubmissionController::setVersion);
			
		
		// optional comment/message to editor
		messageField.setMaxLength(500);
		messageField.setMaxWidth("100%");
		messageField.setHeight("7em");
		
		subBinder.forField(messageField)
				.withValidator(new StringLengthValidator("Maximum 1000 characters.", 0, 1000))
				.bind(ResubmissionController::getResearcherMessage,
						ResubmissionController::setResearcherMessage);
		
		
		subBinder.withValidator(value -> ((MemoryBuffer) upload.getReceiver()).getFileName() != "", "File required.");
		
		submitButton.addClickListener(event -> {
			try {
				// attempt to fill paperSubmission with form values
				subBinder.writeBean(subController);
				subController.setInputStream(((MemoryBuffer) upload.getReceiver()).getInputStream());
				subController.setFilename(((MemoryBuffer) upload.getReceiver()).getFileName());
				
			    // save to data
				subController.createResubmission();
			    
				Notification.show("Resubmission made.");
				
				UI.getCurrent().navigate("researcher");
				
			} catch (ValidationException ex){
				for (ValidationResult c : ex.getValidationErrors()) {
					Notification.show(c.getErrorMessage());
				}
				
			} catch (IOException ex) {
		    	Notification.show("bad io: " + ex.getMessage());						
			}
		});
		
		backButton.addClickListener(
				e -> UI.getCurrent().navigate("researcher"));
		
		
		titleField.setEnabled(false);
		versionField.setEnabled(false);
		messageField.setEnabled(false);
		
		submissionForm.add(paperSelect);
		submissionForm.addFormItem(titleField, "Paper Title");
		submissionForm.addFormItem(versionField, "Version");
		submissionForm.add(messageField);
		submissionForm.add(upload);
		
		submissionForm.setColspan(paperSelect, 2);
		submissionForm.setColspan(titleField, 2);
		submissionForm.setColspan(messageField, 2);
		
		add(heading, submissionForm, submitButton, backButton);
		
	}
}
