package com.packagename.myapp;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextArea;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;

@PageTitle("Resubmission | Vaadin CRM")
//Child of MainLayout Right side of Main Page
//Which has the path localhost:8080/dashboard
@Route(value = "resubmit")
public class ResubmissionPage extends VerticalLayout {
	
	private class Journal {
		private String name;
		
		public Journal(String name) {
			this.name = name;
		}
		
		public String getName() {
			return this.name;
		}
	}
	
	public ResubmissionPage() {
		
		H1 header = new H1("Resubmit a Journal");
		
		//New form layout
		FormLayout layout = new FormLayout();
		
		//Temporary journal list
		List<Journal> journalList = Arrays.asList(
				new Journal("Journal 1"),
				new Journal("Journal 2"),
				new Journal("Journal 3"));
		
		//Make new select component
		Select<Journal> journalSelect = new Select<>();
		
		//Names of journal in the select
		journalSelect.setItemLabelGenerator(Journal::getName);
		journalSelect.setItems(journalList);
		
		//Comment area
		TextArea comments = new TextArea("Message to Editor");
		comments.setPlaceholder("Message to Editor");
		comments.setMaxLength(500);
		comments.setMaxWidth("66%");
		comments.setHeight("7em");
		
		//Upload file
		MemoryBuffer memBuffer = new MemoryBuffer();
		Upload upload = new Upload(memBuffer);
		upload.addFinishedListener(e -> {
			
		    //Read the contents of the buffered file from inputStream
		    try (InputStream inputStream = memBuffer.getInputStream()){
			    // create new file in 'uploaded' directory
			    File f = new File("uploaded\\" + memBuffer.getFileName());
			    f.createNewFile();

			    //Copy uploaded content to f
			    FileUtils.copyInputStreamToFile(inputStream, f);
			    Notification.show("file created: " + f.getAbsolutePath());
			    
		    } catch(IOException ex) {
		    	Notification.show("bad io: " + ex.getMessage());
		    }
		    
		});
		
		//Confirmation message
		Dialog confirm = new Dialog();
		confirm.add(new Label("Resubmission Sent."));
		
		//Submit button
		Button resubmit = new Button("Resubmit", 
				e -> confirm.open());
		
		//Back button to take user back to dashboard
		Button back = new Button("Back",
				e -> UI.getCurrent().navigate("dashboard"));
		
		resubmit.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		
		//Add any components to the layout
		layout.setColspan(comments, 2);
		layout.addFormItem(journalSelect, "Journal");
		layout.add(comments);
		add(header,layout,upload,resubmit, back);
	}
	
	
}
