package com.packagename.myapp;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;

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
		
		// example journal list
		List<TempJournal> journalList = Arrays.asList(
				new TempJournal("ExJournal1"),
				new TempJournal("ExJournal2"),
				new TempJournal("ExJournal3"));
		
		Select<TempJournal> journalSelect = new Select<>();
		
		// let names of journals be shown in selection
		journalSelect.setItemLabelGenerator(TempJournal::getName);
		journalSelect.setItems(journalList);


		// optional field for editor email
		EmailField editorEmailField = new EmailField();
		editorEmailField.setClearButtonVisible(true);
		editorEmailField.setErrorMessage("Please enter a valid email address");
		editorEmailField.setRequiredIndicatorVisible(false);
		
		
		// optional comment/message to editor
		TextArea commentsField = new TextArea("Message to Editor");
		commentsField.setMaxLength(500);
		commentsField.setMaxWidth("100%");
		commentsField.setHeight("7em");
		
		
		// optional field for editor email
		EmailField reviewerEmailsField = new EmailField("Reviewer Email Nominations");
		reviewerEmailsField.setClearButtonVisible(true);
		reviewerEmailsField.setErrorMessage("Please enter a valid email addresses separated by commas");
		reviewerEmailsField.setRequiredIndicatorVisible(false);
		
		
		// submission file upload
		MemoryBuffer memBuffer = new MemoryBuffer();
		Upload upload = new Upload(memBuffer);
		upload.addFinishedListener(e -> {
			
		    // read the contents of the buffered file from inputStream
		    try (InputStream inputStream = memBuffer.getInputStream()){
			    // create new file in 'uploaded' directory
			    File f = new File("uploaded\\" + memBuffer.getFileName());
			    f.createNewFile();

			    // copy uploaded content to f
			    FileUtils.copyInputStreamToFile(inputStream, f);
			    Notification.show("file created: " + f.getAbsolutePath());
			    
		    } catch(IOException ex) {
		    	Notification.show("bad io: " + ex.getMessage());
		    }
		    
		});
		
		
		Button submit = new Button("Submit");
		
		Button back = new Button("Go back",
				e -> UI.getCurrent().navigate("dashboard"));
		
		
		form.addFormItem(journalSelect, "Journal");
		form.addFormItem(editorEmailField, "Editor Email");
		form.setColspan(commentsField, 2);
		form.setColspan(reviewerEmailsField, 2);
		form.add(commentsField, reviewerEmailsField);
		add(form, upload, submit, back);
		
	}
}
