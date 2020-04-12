package com.packagename.myapp;

import java.io.IOException;
import java.util.ArrayList;

import com.packagename.myapp.model.JournalEditor;
import com.packagename.myapp.model.JsonModel;
import com.packagename.myapp.model.Paper;
import com.packagename.myapp.model.Submission;
import com.packagename.myapp.model.User;
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
		
		//Below are arraylists of the different objects we use to grab data with the try and catch blocks.
		ArrayList<Paper> papers;
		ArrayList<Submission> submissions;
		ArrayList<User> users;
		ArrayList<JournalEditor> journalEditors;
		
		//The ArrayList that sorts the papers by editor ID.
		ArrayList<Paper> paperByEditorID = new ArrayList<>();
		//The ArrayList that is added to the grid.
		ArrayList<ActivePaper> actPaper = new ArrayList<>();
		
		//TODO make arraylist that grabs the filtered journal things.
		
		//TODO The arraylist below is the arrayList that should be added to the grid. WIP
		ArrayList<JournalHistory> jHistory = new ArrayList<>(); 
		
		//These try and catch blocks just get the data for that respective object.
		try {
			papers = new ArrayList<>(JsonModel.getPaperData().values());
		} catch(IOException e) {
			e.printStackTrace();
			papers = new ArrayList<>();
		}
		
		try {
			submissions = new ArrayList<>(JsonModel.getSubmissionData().values());
		} catch(IOException e) {
			e.printStackTrace();
			submissions = new ArrayList<>();
		}
		
		try {
			users = new ArrayList<>(JsonModel.getUserData().values());
		} catch(IOException e) {
			e.printStackTrace();
			users = new ArrayList<>();
		}
		
		try {
			journalEditors = new ArrayList<>(JsonModel.getJournalEditorData().values());
		} catch(IOException e) {
			e.printStackTrace();
			journalEditors = new ArrayList<>();
		}
		
		//Just a temporary ID for now
		String tempID = "JaneDoe";
		//Filter the papers and get the papers that match the user ID, putting it in paperByEditor.
		for (Paper paper:papers) {
			if (tempID.equals(paper.getEditorID())) {
				paperByEditorID.add(paper);
			}
		}
		
		//This nested for loop makes it so that papers should be added to the grid for that specific user.
		for (Paper paper:paperByEditorID) {
			for (Submission submission:submissions) {
				if (paper.getPaperID() == submission.getPaperID()) {
					for (User user:users) {
						if (user.getUserID().equals(paper.getResearcherID())) {
							actPaper.add(new ActivePaper(paper,submission,user));
						}
					}
				}
			}
		}
		
		//TODO Filter the journals??? (Ask Sean or refer to RM)
		//The filtered arrayList should be added to the second grid. The Arraylist should already be 
		//declared as jHistory above.
		
	
		//TODO Do the same above, except filter in terms of submission history (Ask Sean or refer to RM)
		//The filtered ArrayList should be added to the third grid.
		
		//The first grid.
		Label first = new Label("Active Papers");
		Grid<ActivePaper> activePapers = new Grid<>();
		activePapers.setItems(actPaper);
		activePapers.addColumn(ActivePaper::getPaperTitle).setHeader("Paper");
		activePapers.addColumn(ActivePaper::getVersion).setHeader("Version");
		activePapers.addColumn(ActivePaper::getResearcher).setHeader("Researcher");
		activePapers.addColumn(ActivePaper::getStatus).setHeader("Status");

		//The second grid.
		Label second = new Label("Journal History");
		Grid<JournalHistory> journalHistory = new Grid<>();
		journalHistory.setItems(jHistory);
		journalHistory.addColumn(JournalHistory::getPaperTitle).setHeader("Paper");
		journalHistory.addColumn(JournalHistory::getResearcher).setHeader("Researcher");
		journalHistory.addColumn(JournalHistory::getYear).setHeader("Collection Halfyear");
		journalHistory.addColumn(JournalHistory::getDecisionDate).setHeader("Last Decision Date");
		journalHistory.addColumn(JournalHistory::getStatus).setHeader("Status");
		
		//The third grid.
		Label third = new Label("Submission History");
		Grid<SubmissionHistory> submissionHistory = new Grid<>();
		submissionHistory.addColumn(SubmissionHistory::getVersion).setHeader("Version");
		submissionHistory.addColumn(SubmissionHistory::getSubDate).setHeader("Submission Date");
		submissionHistory.addColumn(SubmissionHistory::getResubDeadline).setHeader("Resubmission Deadline");
		submissionHistory.addColumn(SubmissionHistory::getReviewDeadline).setHeader("Review Deadline");
		submissionHistory.addColumn(SubmissionHistory::getEditor).setHeader("Editor");
		
		//Back button that sends you back to the dashboard.
		Button back = new Button("Back",
				e -> UI.getCurrent().navigate("dashboard"));
		
		back.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		
		add(first,activePapers,second,journalHistory,third,submissionHistory,back);
		
		
		
	}
	
}
