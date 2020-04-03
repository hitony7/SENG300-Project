package com.packagename.myapp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Reviewer | Vaadin CRM")
//Child of MainLayout Right side of Main Page
//Which has the path localhost:8080/dashboard
@Route(value = "sendEditor", layout = MainLayout.class)

public class SendEditorPage extends VerticalLayout {

	public SendEditorPage() {
		
		//Header
		H2 header = new H2("New Submission to an Editor");
		
		//New form layout
		FormLayout layout = new FormLayout();
		
		//Make selection fields.
		Select<String> papers = new Select<>();
		Select<String> userList = new Select<>();

		//Make a list of temporary papers
		papers.setLabel("Paper");
		papers.setItems("Paper1","Paper2","Paper3");
		
		//Make a list of temporary editors
		userList.setLabel("Editor");
		userList.setItems("Editor 1","Editor 2","Editor 3");
		
		//Add to layout
		layout.add(papers,userList);
		
		//Comment area for the reviewer
		TextArea comment = new TextArea("Comments to the editor");
		comment.setPlaceholder("Comments for the editor...");
		comment.setHeight("10em");
		comment.setWidth("50%");
		
		
		Dialog confirm = new Dialog();
		confirm.add(new Label("Submission Sent"));
		
		Button submit = new Button("Submit",
				e -> confirm.open());
		
				
				
		Button back = new Button("Back",
				e -> UI.getCurrent().navigate("reviewer"));
		
		back.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		submit.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		
		add(header,layout,comment,submit,back);
		
	}
	
}
