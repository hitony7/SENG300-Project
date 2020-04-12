package com.packagename.myapp;

import java.io.IOException;

import com.packagename.myapp.control.NewSubmissionController;
import com.packagename.myapp.model.Journal;
import com.packagename.myapp.model.User;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.formlayout.FormLayout.FormItem;
import com.vaadin.flow.component.grid.Grid;
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
import com.vaadin.flow.data.validator.StringLengthValidator;

public class SubmissionLayout extends VerticalLayout{
	
	NewSubmissionController controller;
	
	private TextField titleField = new TextField();
	private TextField versionField = new TextField();
	private Select<Journal> journalSelect = new Select<>();
	private Select<User> editorEmailSelect = new Select<>();
	private TextArea messageField = new TextArea("Message to Editor");
	private EmailField reviewerEmailsField = new EmailField("Reviewer Email Nominations");
	private Grid<User> reviewerEmailGrid = new Grid<>();
	private ComboBox<User> reviewerEmailCombo = new ComboBox<>();
	private Button addReviewerButton = new Button("Add Reviewer");
	private FormItem reviewerChoose = new FormItem();
	private MemoryBuffer memBuffer = new MemoryBuffer();
	private Upload upload = new Upload(memBuffer);
	
	private Button submitButton = new Button("Submit");
	private Button backButton = new Button("Back");
	
	
	
	public SubmissionLayout() {
		FormLayout form = new FormLayout();
		setMaxWidth("50em");
		
		Binder<NewSubmissionController> binder = new Binder(NewSubmissionController.class);
		
		// placeholder value
		String researcherID = "yes";
		controller = new NewSubmissionController(researcherID);
		
		
		// title field
		binder.forField(titleField)
				.asRequired("Title required.")
				.withValidator(new StringLengthValidator("Maximum 50 character.", 0, 50))
				.bind(NewSubmissionController::getTitle, NewSubmissionController::setTitle);
		
		
		// version field
		versionField.setEnabled(false);
		versionField.setValue("0.0.0");
		
		
		// journal select
		journalSelect.setItems(controller.getAllJournals());
		// let names of journals be shown in selection
		journalSelect.setItemLabelGenerator(Journal::getJName);
		journalSelect.addValueChangeListener(e -> {
			editorEmailSelect.setItems(controller.getAllEditorsByJournal(e.getValue()));
		});

		binder.forField(journalSelect)
				.asRequired("Journal must be selected.")
				.withConverter(Journal::getJName, null)
				.bind(NewSubmissionController::getJournal, NewSubmissionController::setJournal);

		// optional field for editor email
		editorEmailSelect.setEmptySelectionAllowed(true);
		editorEmailSelect.setEmptySelectionCaption("all editors");
		editorEmailSelect.setItemLabelGenerator(user
				-> user == null ? "" : user.getEmail());
		editorEmailSelect.setItems();
		
		binder.forField(editorEmailSelect)
				.bind(NewSubmissionController::getEditor, NewSubmissionController::setEditor);
		
		
		// optional comment/message to editor
		messageField.setMaxLength(500);
		messageField.setMaxWidth("100%");
		messageField.setHeight("7em");
		
		binder.forField(messageField)
				.withValidator(new StringLengthValidator("Maximum 1000 characters.", 0, 1000))
				.bind(NewSubmissionController::getResearcherMessage,
						NewSubmissionController::setResearcherMessage);
		
		
		// optional field for reviewer email
		reviewerEmailsField.setClearButtonVisible(true);
		reviewerEmailsField.setErrorMessage(
				"Please enter a valid email addresses separated by commas");
		reviewerEmailsField.setRequiredIndicatorVisible(false);
		
		
		// reviewerEmailGrid
		reviewerEmailCombo.setItems((reviewer, filter)
				-> reviewer.getEmail().startsWith(filter), controller.getAllReviewers());
		reviewerEmailCombo.setItemLabelGenerator(User::getEmail);
		reviewerEmailCombo.setClearButtonVisible(true);
		reviewerEmailCombo.getElement().setAttribute("theme", "small");
		
		/*addReviewerButton.addClickListener(e
				-> {
					
				})*/
		
		
		
		binder.withValidator(value -> memBuffer.getFileName() != "", "File required.");
		
		Button submit = new Button("Submit",
				event -> {
					try {
						// attempt to fill paperSubmission with form values
						binder.writeBean(controller);						
						controller.setInputStream(memBuffer.getInputStream());
						controller.setFilename(memBuffer.getFileName());
						
						System.out.println("Editor email: " + controller.getEditor());
						
					    // save to data
						controller.newResearcherSubmission();
					    
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
		form.addFormItem(editorEmailSelect, "Editor Email");
		form.setColspan(messageField, 2);
		//form.setColspan(reviewerEmailsField, 2);

		reviewerChoose.add(reviewerEmailCombo, addReviewerButton);
		
		form.add(messageField, reviewerEmailGrid, reviewerChoose);
		add(form, upload, submit, back);
		
	}
}
