package com.packagename.myapp;

import java.util.ArrayList;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Reviewer | Vaadin CRM")
//Child of MainLayout Right side of Main Page
//Which has the path localhost:8080/dashboard
@Route(value = "editor", layout = MainLayout.class)
public class EditorPage extends VerticalLayout{

	public EditorPage() {
		
		Label first = new Label("Active Papers");
		Grid<ActivePapers> activePapers = new Grid<>();
		activePapers.addColumn(ActivePapers::getPaperTitle).setHeader("Paper");
		activePapers.addColumn(ActivePapers::getVersion).setHeader("Version");
		activePapers.addColumn(ActivePapers::getResearcher).setHeader("Researcher");
		activePapers.addColumn(ActivePapers::getStatus).setHeader("Status");
		
		Label second = new Label("Journal History");
		Grid<JournalHistory> journalHistory = new Grid<>();
		journalHistory.addColumn(JournalHistory::getPaperTitle).setHeader("Paper");
		journalHistory.addColumn(JournalHistory::getResearcher).setHeader("Researcher");
		journalHistory.addColumn(JournalHistory::getYear).setHeader("Collection Halfyear");
		journalHistory.addColumn(JournalHistory::getDecisionDate).setHeader("Last Decision Date");
		journalHistory.addColumn(JournalHistory::getStatus).setHeader("Status");
		
		Label third = new Label("Submission History");
		Grid<SubmissionHistory> submissionHistory = new Grid<>();
		submissionHistory.addColumn(SubmissionHistory::getVersion).setHeader("Version");
		submissionHistory.addColumn(SubmissionHistory::getSubDate).setHeader("Submission Date");
		submissionHistory.addColumn(SubmissionHistory::getResubDeadline).setHeader("Resubmission Deadline");
		submissionHistory.addColumn(SubmissionHistory::getReviewDeadline).setHeader("Review Deadline");
		submissionHistory.addColumn(SubmissionHistory::getEditor).setHeader("Editor");
		
		
		Button back = new Button("Back",
				e -> UI.getCurrent().navigate("dashboard"));
		
		back.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		
		add(first,activePapers,second,journalHistory,third,submissionHistory,back);
		
		
		
	}
	
}
