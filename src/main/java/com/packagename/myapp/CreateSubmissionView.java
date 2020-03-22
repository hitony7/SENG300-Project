package com.packagename.myapp;


import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.router.Route;

@Route(value = "submit")
public class CreateSubmissionView extends VerticalLayout{
	
	private class TempJournal {
		private String name;
		
		public TempJournal(String name) {
			this.name = name;
		}
		
		public String getName() {
			return this.name;
		}
	}

	
	public CreateSubmissionView() {
		// example journal list
		List<TempJournal> journalList = Arrays.asList(
				new TempJournal("ExJournal1"),
				new TempJournal("ExJournal2"),
				new TempJournal("ExJournal3"));
		
		Select<TempJournal> journalSelect = new Select<>();
		journalSelect.setLabel("Journal");
		
		// let the journal name be shown in selection
		journalSelect.setItemLabelGenerator(TempJournal::getName);
		journalSelect.setItems(journalList);
		
		
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
		

		add(journalSelect);
		add(upload);
		
	}
}
