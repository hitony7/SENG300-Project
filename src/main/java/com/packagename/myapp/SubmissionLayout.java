package com.packagename.myapp;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import com.packagename.myapp.control.NewPaperSubmission;
import com.packagename.myapp.control.Paper;
import com.packagename.myapp.control.Submission;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.validator.RegexpValidator;
import com.vaadin.flow.data.validator.StringLengthValidator;

public class SubmissionLayout extends VerticalLayout{
	
	private HashMap<Integer, Paper> paperData;
	private HashMap<Pair<Integer, String>, Submission> submissionData;
	
	private TextField titleField = new TextField();
	private TextField versionField = new TextField();
	private Select<TempJournal> journalSelect = new Select<>();
	private TextField editorEmailField = new TextField();
	private TextArea messageField = new TextArea("Message to Editor");
	private EmailField reviewerEmailsField = new EmailField("Reviewer Email Nominations");
	private MemoryBuffer memBuffer = new MemoryBuffer();
	private Upload upload = new Upload(memBuffer);

	private class TempJournal {
		private String name;
		
		public TempJournal(String name) {
			this.name = name;
		}
		
		public String getName() {
			return this.name;
		}
	}
	
	
	
	public SubmissionLayout() {
		FormLayout form = new FormLayout();
		setMaxWidth("50em");
		
		Binder<NewPaperSubmission> binder = new Binder(NewPaperSubmission.class);
		try {
			paperData = JsonReader.getPaperData();
			submissionData = JsonReader.getSubmissionData();
		} catch (IOException e1) {
			e1.printStackTrace();
			paperData = new HashMap<>();
			submissionData = new HashMap<>();
		}
		
		int paperID = JsonReader.getNumberOfPapers(paperData);
		// placeholder value
		int researcherID = 15;
		NewPaperSubmission paperSubmission = new NewPaperSubmission(paperID, researcherID);
		
		// title field		
		binder.forField(titleField)
				.asRequired("Title required.")
				.bind(NewPaperSubmission::getTitle, NewPaperSubmission::setTitle);
		
		
		// version field
		
		versionField.setEnabled(false);
		versionField.setValue("0.0.0");
		
		// example journal list
		List<TempJournal> journalList = Arrays.asList(
				new TempJournal("ExJournal1"),
				new TempJournal("ExJournal2"),
				new TempJournal("ExJournal3"));
		
		// let names of journals be shown in selection
		journalSelect.setItemLabelGenerator(TempJournal::getName);
		journalSelect.setItems(journalList);

		binder.forField(journalSelect)
				.asRequired("Journal must be selected.")
				.withConverter(TempJournal::getName, null)
				.bind(NewPaperSubmission::getJournal,NewPaperSubmission::setJournal);

		// optional field for editor email
		editorEmailField.setClearButtonVisible(true);
		editorEmailField.setPlaceholder("Optional");
		
		binder.forField(editorEmailField)
				.withValidator(new RegexpValidator("Please enter a valid email address",
						"(^" + "([a-zA-Z0-9_\\.\\-+])+" // local
						            + "@" + "[a-zA-Z0-9-.]+" // domain
						            + "\\." + "[a-zA-Z0-9-]{2,}" // tld
						            + "$)?",
			            true))
				.bind(NewPaperSubmission::getEditorEmail, NewPaperSubmission::setEditorEmail);
		
		
		// optional comment/message to editor
		messageField.setMaxLength(500);
		messageField.setMaxWidth("100%");
		messageField.setHeight("7em");
		
		binder.forField(messageField)
				.withValidator(new StringLengthValidator("Maximum 400 characters.", 0, 400))
				.bind(NewPaperSubmission::getResearcherMessage, NewPaperSubmission::setResearcherMessage);
		
		
		// optional field for reviewer email
		reviewerEmailsField.setClearButtonVisible(true);
		reviewerEmailsField.setErrorMessage("Please enter a valid email addresses separated by commas");
		reviewerEmailsField.setRequiredIndicatorVisible(false);
		
		binder.withValidator(value -> memBuffer.getFileName() != "", "File required.");
		
		Button submit = new Button("Submit",
				event -> {
					try {
						// attempt to fill paperSubmission with form values
						binder.writeBean(paperSubmission);						
						paperSubmission.setInputStream(memBuffer.getInputStream());
						paperSubmission.setFilename(memBuffer.getFileName());
						
						System.out.println("Editor email: " + paperSubmission.getEditorEmail());
						
					    // save to data
						JsonReader.newResearcherSubmission(paperData, submissionData, paperSubmission);
					    
						Notification.show("Submission made.");
						
					} catch (ValidationException ex){
						for (ValidationResult c : ex.getValidationErrors()) {
							Notification.show(c.getErrorMessage());
						}
						
					} catch (IOException ex) {
				    	Notification.show("bad io: " + ex.getMessage());						
					}
				});
		
		Button back = new Button("Go back",
				e -> UI.getCurrent().navigate("dashboard"));
		
		
		form.addFormItem(titleField, "Paper Title");
		form.addFormItem(versionField, "Version");
		form.addFormItem(journalSelect, "Journal");
		form.addFormItem(editorEmailField, "Editor Email");
		form.setColspan(messageField, 2);
		form.setColspan(reviewerEmailsField, 2);
		form.add(messageField, reviewerEmailsField);
		add(form, upload, submit, back);
		
	}
}
