package com.packagename.myapp;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.tuple.Pair;

import com.packagename.myapp.control.Paper;
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
import com.vaadin.flow.data.validator.RegexpValidator;
import com.vaadin.flow.data.validator.StringLengthValidator;

public class SubmissionLayout extends VerticalLayout{

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
		HashMap<Integer,Paper> paperData = JsonReader.getPaperData();
		HashMap<Pair<Integer, String>, Paper> submissionData = JsonReader.getSubmissionData();
		NewPaperSubmission paperSubmission = new NewPaperSubmission();
		
		// example journal list
		List<TempJournal> journalList = Arrays.asList(
				new TempJournal("ExJournal1"),
				new TempJournal("ExJournal2"),
				new TempJournal("ExJournal3"));
		
		Select<TempJournal> journalSelect = new Select<>();
		
		// let names of journals be shown in selection
		journalSelect.setItemLabelGenerator(TempJournal::getName);
		journalSelect.setItems(journalList);

		binder.forField(journalSelect)
				.asRequired()
				.withConverter(TempJournal::getName, null)
				.bind(NewPaperSubmission::getJournal,NewPaperSubmission::setJournal);

		// optional field for editor email
		TextField editorEmailField = new TextField();
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
		TextArea messageField = new TextArea("Message to Editor");
		messageField.setMaxLength(500);
		messageField.setMaxWidth("100%");
		messageField.setHeight("7em");
		
		binder.forField(messageField)
				.withValidator(new StringLengthValidator("Maximum 400 characters.", 0, 400))
				.bind(NewPaperSubmission::getResearcherMessage, NewPaperSubmission::setResearcherMessage);
		
		
		// optional field for reviewer email
		EmailField reviewerEmailsField = new EmailField("Reviewer Email Nominations");
		reviewerEmailsField.setClearButtonVisible(true);
		reviewerEmailsField.setErrorMessage("Please enter a valid email addresses separated by commas");
		reviewerEmailsField.setRequiredIndicatorVisible(false);
		
		
		// submission file upload
		MemoryBuffer memBuffer = new MemoryBuffer();
		Upload upload = new Upload(memBuffer);
		/*upload.addFinishedListener(e -> {
			
		    // read the contents of the buffered file from inputStream
		    try (InputStream inputStream = memBuffer.getInputStream()){
			    // create new file in 'uploaded' directory
			    File f = new File("data\\journals\\" +  memBuffer.getFileName());
			    f.createNewFile();

			    // copy uploaded content to f
			    FileUtils.copyInputStreamToFile(inputStream, f);
			    Notification.show("file created: " + f.getAbsolutePath());
			    
		    } catch(IOException ex) {
		    	Notification.show("bad io: " + ex.getMessage());
		    }
		    
		});*/
		
		
		Button submit = new Button("Submit",
				event -> {
					try {
						// attempt to fill paperSubmission with form values
						binder.writeBean(paperSubmission);
						
						// read the contents of the buffered file from inputStream
						InputStream inputStream = memBuffer.getInputStream();
					    // create new file in 'uploaded' directory
					    File f = new File("data\\journals\\" + paperSubmission.getJournal() + memBuffer.getFileName());
					    f.createNewFile();

					    // copy uploaded content to f
					    FileUtils.copyInputStreamToFile(inputStream, f);
					    Notification.show("file created: " + f.getAbsolutePath());
					    
					    // save to data
					    
					} catch (ValidationException ex){
						Notification.show("invalid fields");
						
					} catch (IOException ex) {
				    	Notification.show("bad io: " + ex.getMessage());						
					}
				});
		
		Button back = new Button("Go back",
				e -> UI.getCurrent().navigate("dashboard"));
		
		
		form.addFormItem(journalSelect, "Journal");
		form.addFormItem(editorEmailField, "Editor Email");
		form.setColspan(messageField, 2);
		form.setColspan(reviewerEmailsField, 2);
		form.add(messageField, reviewerEmailsField);
		add(form, upload, submit, back);
		
	}
}
