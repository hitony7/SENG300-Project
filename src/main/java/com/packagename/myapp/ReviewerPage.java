package com.packagename.myapp;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout.Orientation;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.io.FileReader;
import java.util.Iterator;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

@PageTitle("Reviewer | Vaadin CRM")
//Child of MainLayout Right side of Main Page
//Which has the path localhost:8080/dashboard
@Route(value = "reviewer", layout = MainLayout.class)
public class ReviewerPage extends VerticalLayout {
	
	public ReviewerPage() {
		
		
		setDefaultHorizontalComponentAlignment(Alignment.STRETCH);
		
		Button sendEditor = new Button("Send a submission to an Editor",
				e -> UI.getCurrent().navigate("sendEditor")); 
		
		sendEditor.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		
		JSONParser jsonParser = new JSONParser();

		//Temporary list for reviewer submissions
		ArrayList<ReviewerSubmissions> submissionList = new ArrayList<>();

		//TODO make sure the file name matches
		try(FileReader reader = new FileReader("paper.json")){
			Object obj = jsonParser.parse(reader);
			JSONArray paperArray = (JSONArray) obj;

			paperArray.forEach(emp -> parsePaperObject((JSONObject) emp));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e){
			e.printStackTrace();
		} catch (ParseException e){
			e.printStackTrace();
		}

		ReviewerSubmissions submission1 = new ReviewerSubmissions("submission1","xx-xx-xxxx");
		ReviewerSubmissions submission2 = new ReviewerSubmissions("submission2","xx-xx-xxxx");
		ReviewerSubmissions submission3 = new ReviewerSubmissions("submission3","xx-xx-xxxx");
		
		
		//Add temporary submissions
		submissionList.add(submission1);
		submissionList.add(submission2);
		submissionList.add(submission3);
		
		//Submission Grid
		Grid<ReviewerSubmissions> submitGrid = new Grid<>();
		Label submitLabel = new Label("Submitted Reviews");
		submitGrid.setItems(submissionList);
		submitGrid.addColumn(ReviewerSubmissions::getName).setHeader("Submission Name");
		submitGrid.addColumn(ReviewerSubmissions::getSubmitDate).setHeader("Date Submitted");

		
		//Temporary ArrayList for reviewed papers
		ArrayList<ReviewedPapers> reviewList = new ArrayList<>();
		ReviewedPapers paper1 = new ReviewedPapers("paper1","SomeJournal","XX-XX-XXXX","A Journal","Jane Doe","Accepted");
		ReviewedPapers paper2 = new ReviewedPapers("paper2","SomeJournal","XX-XX-XXXX","A Journal","Jane Doe","Rejected");
		ReviewedPapers paper3 = new ReviewedPapers("paper3","SomeJournal","XX-XX-XXXX","A Journal","Jane Doe","In Review");
		
		//Add temporary reviewed papers
		reviewList.add(paper1);
		reviewList.add(paper2);
		reviewList.add(paper3);
		
		//Review Grid
		Grid<ReviewedPapers> reviewGrid = new Grid<>();
		Label reviewLabel = new Label("Reviewed Journals");
		reviewGrid.setItems(reviewList);
		reviewGrid.addColumn(ReviewedPapers::getPaper).setHeader("Paper");
		reviewGrid.addColumn(ReviewedPapers::getJournal).setHeader("Journal");
		reviewGrid.addColumn(ReviewedPapers::getDate).setHeader("Date");
		reviewGrid.addColumn(ReviewedPapers::getDesc).setHeader("Description");
		reviewGrid.addColumn(ReviewedPapers::getReviewer).setHeader("Reviewer");
		reviewGrid.addColumn(ReviewedPapers::getStatus).setHeader("Status");
		
		//Layout for the page
		SplitLayout layout = new SplitLayout();
		layout.setOrientation(Orientation.VERTICAL);
		layout.addToPrimary(submitLabel);
		layout.addToPrimary(submitGrid);
		layout.addToSecondary(reviewLabel);
		layout.addToSecondary(reviewGrid);
		
		Button back = new Button("Back",
				e -> UI.getCurrent().navigate("dashboard"));
		back.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		
		add(sendEditor,layout,back);
	}

	private static void parsePaperObject(JSONObject papers){
		//TODO make sure it grabs the right name
		JSONObject paperObject = (JSONObject) papers.get("paperid");

	}
}
