package com.packagename.myapp;

import java.io.IOException;
import java.util.HashMap;

import org.apache.commons.lang3.tuple.Pair;

import com.packagename.myapp.control.NewSubmissionController;
import com.packagename.myapp.model.base.EditorJournal;
import com.packagename.myapp.model.base.Journal;
import com.packagename.myapp.model.base.JsonModel;
import com.packagename.myapp.model.base.NominatedReviewer;
import com.packagename.myapp.model.base.Paper;
import com.packagename.myapp.model.base.Submission;
import com.packagename.myapp.model.base.User;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
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

public class SubmissionLayout extends VerticalLayout{
	
	private HashMap<String,User> userData;
	private HashMap<Integer,Paper> paperData;
	private HashMap<Pair<Integer,String>,Submission> submissionData;
	private HashMap<String,Journal> journalData;
	private HashMap<String,EditorJournal> journalEditorData;
	private HashMap<Pair<Integer,String>,NominatedReviewer> nominatedReviewerData;
	
	private NewSubmissionController subController;
	private ReviewerListController revController;
	
	private FormLayout submissionForm = new FormLayout();
	private TextField titleField = new TextField();
	private TextField versionField = new TextField();
	private Select<Journal> journalSelect = new Select<>();
	private Select<User> editorEmailSelect = new Select<>();
	private TextArea messageField = new TextArea("Message to Editor");
	private Grid<User> reviewerEmailGrid = new Grid<>();
	private ComboBox<User> reviewerEmailCombo = new ComboBox<>("Nominate Reviewer");
	private Button addReviewerButton = new Button("Add");
	private Button removeReviewerButton = new Button("Remove");
	private HorizontalLayout reviewerListForm = new HorizontalLayout();
	private Upload upload = new Upload(new MemoryBuffer());
	
	private Button submitButton = new Button("Submit");
	private Button backButton = new Button("Back");
	
	
	
	public SubmissionLayout() {
		setMaxWidth("50em");
		
		Binder<NewSubmissionController> subBinder = new Binder(NewSubmissionController.class);

		try {
			userData = JsonModel.getUserData();
			paperData = JsonModel.getPaperData();
			submissionData = JsonModel.getSubmissionData();
			journalData = JsonModel.getJournalData();
			journalEditorData = JsonModel.getEditorJournalData();
			nominatedReviewerData = JsonModel.getNominatedReviewerData();
		} catch (IOException e1) {
			e1.printStackTrace();
			userData = new HashMap<>();
			paperData = new HashMap<>();
			submissionData = new HashMap<>();
			journalData = new HashMap<>();
			journalEditorData = new HashMap<>();
			nominatedReviewerData = new HashMap<>();
		}
		
		// placeholder value
		String researcherID = "admin";
		
		subController = new NewSubmissionController(userData, paperData, submissionData,
				journalData, journalEditorData, nominatedReviewerData, researcherID);
		revController = new ReviewerListController(userData);
		
		// title field
		subBinder.forField(titleField)
				.asRequired("Title required.")
				.withValidator(new StringLengthValidator("Maximum 50 character.", 0, 50))
				.bind(NewSubmissionController::getTitle, NewSubmissionController::setTitle);
		
		
		// version field
		versionField.setEnabled(false);
		versionField.setValue("0.0.0");
		
		
		// journal select
		journalSelect.setItems(subController.getAllJournals());
		// let names of journals be shown in selection
		journalSelect.setItemLabelGenerator(Journal::getJName);
		journalSelect.addValueChangeListener(e -> {
			editorEmailSelect.setItems(subController.getAllEditorsByJournal(e.getValue()));
		});

		subBinder.forField(journalSelect)
				.asRequired("Journal must be selected.")
				.withConverter(Journal::getJName, null)
				.bind(NewSubmissionController::getJournal, NewSubmissionController::setJournal);

		// optional field for editor email
		editorEmailSelect.setEmptySelectionAllowed(true);
		editorEmailSelect.setEmptySelectionCaption("all editors");
		editorEmailSelect.setItemLabelGenerator(user
				-> user == null ? "" : user.getEmail());
		editorEmailSelect.setItems();
		
		subBinder.forField(editorEmailSelect)
				.bind(NewSubmissionController::getEditor, NewSubmissionController::setEditor);
		
		
		// optional comment/message to editor
		messageField.setMaxLength(500);
		messageField.setMaxWidth("100%");
		messageField.setHeight("7em");
		
		subBinder.forField(messageField)
				.withValidator(new StringLengthValidator("Maximum 1000 characters.", 0, 1000))
				.bind(NewSubmissionController::getResearcherMessage,
						NewSubmissionController::setResearcherMessage);
		
		
		
		// reviewerEmailGrid
		reviewerEmailGrid.setItems(revController.getReviewerList());
		reviewerEmailGrid.addColumn(User::getEmail).setHeader("Email");
		reviewerEmailGrid.addColumn(User::getField).setHeader("Field").setAutoWidth(true);
		reviewerEmailGrid.setHeightByRows(true);
		reviewerEmailGrid.setMaxWidth("20em");
		reviewerEmailGrid.getElement().setAttribute("theme", "small");
		
		reviewerEmailCombo.setItems((reviewer, filter)
				-> reviewer.getEmail().startsWith(filter), subController.getAllReviewers());
		reviewerEmailCombo.setItemLabelGenerator(User::getEmail);
		reviewerEmailCombo.setClearButtonVisible(true);
		reviewerEmailCombo.getElement().setAttribute("theme", "small");
		
		addReviewerButton.addClickListener(e -> {
			revController.addReviewer(reviewerEmailCombo.getValue());
			reviewerEmailGrid.getDataProvider().refreshAll();
		});
		
		removeReviewerButton.setMinWidth("5em");
		removeReviewerButton.addClickListener(e -> {
			revController.removeReviewer(reviewerEmailCombo.getValue());
			reviewerEmailGrid.getDataProvider().refreshAll();
		});

		
		subBinder.withValidator(value -> ((MemoryBuffer) upload.getReceiver()).getFileName() != "", "File required.");
		
		submitButton.addClickListener(event -> {
			try {
				// attempt to fill paperSubmission with form values
				subBinder.writeBean(subController);						
				subController.setInputStream(((MemoryBuffer) upload.getReceiver()).getInputStream());
				subController.setFilename(((MemoryBuffer) upload.getReceiver()).getFileName());
				
			    // save to data
				subController.newResearcherSubmission(revController);
			    
				Notification.show("Submission made.");
				
				upload.setReceiver(new MemoryBuffer());
				
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
		
		
		submissionForm.addFormItem(titleField, "Paper Title");
		submissionForm.addFormItem(versionField, "Version");
		submissionForm.addFormItem(journalSelect, "Journal");
		submissionForm.addFormItem(editorEmailSelect, "Editor Email");
		reviewerListForm.add(reviewerEmailGrid, reviewerEmailCombo, addReviewerButton, removeReviewerButton);
		submissionForm.add(messageField);
		submissionForm.add(reviewerListForm);
		submissionForm.add(upload);
		
		submissionForm.setColspan(messageField, 2);
		submissionForm.setColspan(reviewerListForm, 2);
		
		add(submissionForm, submitButton, backButton);
		
	}
}
