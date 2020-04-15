package com.packagename.myapp;

import com.packagename.myapp.control.ResearcherPageController;
import com.packagename.myapp.model.base.*;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@PageTitle("set-status| Vaadin CRM")
//Child of MainLayout Right side of Main Page
//Which has the path localhost:8080/dashboard
@Route(value = "set-status", layout = MainLayout.class)
public class SetStatusPage extends VerticalLayout {
	public static String pName ="none";
	public static String vName ="none";

	public SetStatusPage() throws IOException {
		
		H1 header = new H1("Set Status");

		HashMap<Pair<Integer, String>, Submission> submissionData = JsonModel.getSubmissionData();
		HashMap<String, User> userData;
		HashMap<Integer, Paper> paperData;
		HashMap<String, Journal> journalData;
		try {
			userData = JsonModel.getUserData();
			paperData = JsonModel.getPaperData();
			submissionData = JsonModel.getSubmissionData(); //
			journalData = JsonModel.getJournalData();
		} catch (IOException e1) {
			e1.printStackTrace();
			userData = new HashMap<>();
			paperData = new HashMap<>();
			submissionData = new HashMap<>();
			journalData = new HashMap<>();
		}



		Label paperSelect = new Label("Selected Paper");
		TextField text = new TextField();
		text.setReadOnly(true);
		text.setValue(pName);
		//Select<Paper> selectPaper = new Select();
		
		Label status = new Label("Set Status");
		Select setStatus = new Select();
		setStatus.setItems("Major revison","Minor revision", "Rejected", "Accepted");
		
		TextArea comment = new TextArea("Comment");
		comment.setPlaceholder("Message to the researcher...");
		comment.getStyle().set("minHeight","150px");
		comment.getStyle().set("minWidth", "50%");
		
		TextField setResub = new TextField("Set Resubmission Deadline");
		
		Dialog popup = new Dialog();
		popup.add(new Label("Status Set"));
		
		Button set = new Button("Set",
				e -> popup.open());
		
		Button back = new Button("back",
				e -> UI.getCurrent().navigate("editor"));
		
		set.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		back.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		
		add(header,paperSelect,text,status,setStatus,comment,setResub,popup,set,back);
		
	}
	
}
