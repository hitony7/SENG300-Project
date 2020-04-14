package com.packagename.myapp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.lang3.tuple.Pair;

import com.packagename.myapp.control.EditorPageController;
import com.packagename.myapp.model.base.EditorJournal;
import com.packagename.myapp.model.base.JsonModel;
import com.packagename.myapp.model.base.Paper;
import com.packagename.myapp.model.base.Submission;
import com.packagename.myapp.model.base.User;
import com.packagename.myapp.model.composite.PaperEntry;
import com.packagename.myapp.model.composite.SubmissionHistory;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

import com.vaadin.flow.component.html.H3;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Reviewer | Vaadin CRM")
//Child of MainLayout Right side of Main Page
//Which has the path localhost:8080/dashboard
@Route(value = "editor", layout = MainLayout.class)
public class EditorPage extends VerticalLayout{

	private HashMap<String,User> userData;
	private HashMap<Integer,Paper> paperData;
	private HashMap<Pair<Integer,String>,Submission> submissionData;
	private HashMap<String,EditorJournal> journalEditorData;

	private EditorPageController controller;

	H3 first = new H3("Assigned Active Papers");
	Grid<PaperEntry> activePapers = new Grid<>();
	H3 second = new H3("Journal History");
	Grid<PaperEntry> journalHistory = new Grid<>();
	H3 third = new H3("Submission History");
	Grid<SubmissionHistory> submissionHistory = new Grid<>();

	public EditorPage() {


		HorizontalLayout topButtons = new HorizontalLayout();
		Button status = new Button("Status",
				e -> UI.getCurrent().navigate("set-status"));
		Button request = new Button("Request Reviews",
				e -> UI.getCurrent().navigate("request-review"));
		Button choosePaper = new Button("Choose Paper",
				e -> UI.getCurrent().navigate("choose-paper"));

		status.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		request.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		choosePaper.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

		topButtons.add(status,request,choosePaper);



		//Below are arraylists of the different objects we use to grab data with the try and catch blocks.
		ArrayList<Paper> papers;
		ArrayList<Submission> submissions;
		ArrayList<User> users;
		//ArrayList<Journal> journals;
		ArrayList<EditorJournal> editorJournals;

		//The ArrayList that sorts the papers by editor ID.
		ArrayList<Paper> paperByEditorID = new ArrayList<>();
		//The ArrayList that sorts the editorJournals by ID.
		ArrayList<EditorJournal> editorJournalByID = new ArrayList<>();

		//The ArrayLists that are added to the grids.
		//ArrayList<ActivePaper> actPaper = new ArrayList<>();
		ArrayList<SubmissionHistory> subHistory = new ArrayList<>();
		//TODO The arraylist below is the arrayList that should be added to the grid. WIP
		//ArrayList<JournalHistory> jHistory = new ArrayList<>();

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
			editorJournals = new ArrayList<>(JsonModel.getEditorJournalData().values());
		} catch(IOException e) {
			e.printStackTrace();
			editorJournals = new ArrayList<>();
		}


		try {
			userData = JsonModel.getUserData();
			paperData = JsonModel.getPaperData();
			submissionData = JsonModel.getSubmissionData();
			journalEditorData = JsonModel.getEditorJournalData();
		} catch (IOException e1) {
			e1.printStackTrace();
			userData = new HashMap<>();
			paperData = new HashMap<>();
			submissionData = new HashMap<>();
			journalEditorData = new HashMap<>();
		}

		// placeholder value
		String userID = "TrTr564";

		controller = new EditorPageController(userData, paperData, submissionData,
				journalEditorData, userID);


		//The first grid.
		activePapers.setItems(controller.getActivePapers());
		activePapers.addColumn(PaperEntry::getPaperTitle).setHeader("Paper");
		activePapers.addColumn(PaperEntry::getVersion).setHeader("Version");
		activePapers.addColumn(PaperEntry::getResearcherName).setHeader("Researcher");
		activePapers.addColumn(PaperEntry::getStatus).setHeader("Status");

		activePapers.addSelectionListener(selection -> {
			PaperEntry entry = selection.getFirstSelectedItem().orElse(null);

			if (entry == null) {
				submissionHistory.setItems();
			} else {
				journalHistory.deselectAll();
				submissionHistory.setItems(controller.getSubmissionHistory(entry));
				SetStatusPage.pName = entry.getPaperTitle();
				ChoosePaperPage.pName = entry.getPaperTitle();
				SetStatusPage.vName = entry.getVersion();
			}
			submissionHistory.getDataProvider().refreshAll();
		});

		//The second grid.
		journalHistory.setItems(controller.getJournalHistory());
		journalHistory.addColumn(PaperEntry::getPaperTitle).setHeader("Paper");
		journalHistory.addColumn(PaperEntry::getResearcherName).setHeader("Researcher");
		journalHistory.addColumn(PaperEntry::getCollectionYearHalfYear).setHeader("Collection Halfyear");
		journalHistory.addColumn(PaperEntry::getLastDecisionDate).setHeader("Last Decision Date");
		journalHistory.addColumn(PaperEntry::getStatus).setHeader("Status");

		journalHistory.addSelectionListener(selection -> {
			PaperEntry entry = selection.getFirstSelectedItem().orElse(null);

			if (entry == null) {
				submissionHistory.setItems();
			} else {
				activePapers.deselectAll();
				submissionHistory.setItems(controller.getSubmissionHistory(entry));
			}
			submissionHistory.getDataProvider().refreshAll();
		});

		//The third grid.
		submissionHistory.addColumn(SubmissionHistory::getVersion).setHeader("Version");
		submissionHistory.addColumn(SubmissionHistory::getSubmissionDate).setHeader("Submission Date");
		submissionHistory.addColumn(SubmissionHistory::getResubmissionDeadline).setHeader("Resubmission Deadline");
		submissionHistory.addColumn(SubmissionHistory::getReviewDeadline).setHeader("Review Deadline");
		submissionHistory.addColumn(SubmissionHistory::getEditorEmail).setHeader("Editor");


		//Back button that sends you back to the dashboard.
		Button back = new Button("Back",
				e -> UI.getCurrent().navigate("dashboard"));

		back.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

		add(topButtons,first,activePapers,second,journalHistory,third,submissionHistory,back);



	}

}

