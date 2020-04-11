package com.packagename.myapp;

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
		
		
		//Temporary list for reviewer submissions
		ArrayList<ReviewerSubmissions> submissionList = new ArrayList<>();
		ReviewerSubmissions submission1 = new ReviewerSubmissions("Journal 1","Paper 1","2","Jane Doe","XX-XX-XXXX","John Doe");
		ReviewerSubmissions submission2 = new ReviewerSubmissions("Journal 2","Paper 2","3","Jane Doe","XX-XX-XXXX","John Doe");
		ReviewerSubmissions submission3 = new ReviewerSubmissions("Journal 3","Paper 3","4","Jane Doe","XX-XX-XXXX","John Doe");
		
		
		//Add temporary submissions
		submissionList.add(submission1);
		submissionList.add(submission2);
		submissionList.add(submission3);
		
		//Submission Grid
		Grid<ReviewerSubmissions> submitGrid = new Grid<>();
		Label submitLabel = new Label("Review Requests");
		submitGrid.setItems(submissionList);
		submitGrid.addColumn(ReviewerSubmissions::getJournal).setHeader("Journal");
		submitGrid.addColumn(ReviewerSubmissions::getPaper).setHeader("Paper");
		submitGrid.addColumn(ReviewerSubmissions::getVersion).setHeader("Version");
		submitGrid.addColumn(ReviewerSubmissions::getResearcher).setHeader("Researcher");
		submitGrid.addColumn(ReviewerSubmissions::getReviewDeadline).setHeader("ReviewDeadline");
		submitGrid.addColumn(ReviewerSubmissions::getEditor).setHeader("Editor");

		
		//Temporary ArrayList for reviewed papers
		ArrayList<ReviewedPapers> reviewList = new ArrayList<>();
		ReviewedPapers paper1 = new ReviewedPapers("Journal 1","Paper 1","1","Jane Doe","XX-XX-XXXX","John Doe");
		ReviewedPapers paper2 = new ReviewedPapers("Journal 2","Paper 2","2","Jane Doe","XX-XX-XXXX","John Doe");
		ReviewedPapers paper3 = new ReviewedPapers("Journal 3","Paper 3","3","Jane Doe","XX-XX-XXXX","John Doe");
		
		//Add temporary reviewed papers
		reviewList.add(paper1);
		reviewList.add(paper2);
		reviewList.add(paper3);
		
		//Review Grid
		Grid<ReviewedPapers> reviewGrid = new Grid<>();
		Label reviewLabel = new Label("Review History");
		reviewGrid.setItems(reviewList);
		reviewGrid.addColumn(ReviewedPapers::getJournal).setHeader("Journal");
		reviewGrid.addColumn(ReviewedPapers::getPaper).setHeader("Paper");
		reviewGrid.addColumn(ReviewedPapers::getVersion).setHeader("Version");
		reviewGrid.addColumn(ReviewedPapers::getResearcher).setHeader("Researcher");
		reviewGrid.addColumn(ReviewedPapers::getReviewDeadline).setHeader("Review Deadline");
		reviewGrid.addColumn(ReviewedPapers::getEditor).setHeader("Editor");
		
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
	
}
