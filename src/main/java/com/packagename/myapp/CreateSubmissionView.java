package com.packagename.myapp;


import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "submit")
public class CreateSubmissionView extends VerticalLayout{
	
	
	public CreateSubmissionView() {
		H1 heading = new H1("Create a New Submission");
		
		SubmissionLayout submission = new SubmissionLayout();

		add(heading, submission);
		
	}
}
