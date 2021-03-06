package com.packagename.myapp;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

import org.apache.commons.lang3.tuple.Pair;

import com.packagename.myapp.control.ResearcherPageController;
import com.packagename.myapp.model.base.Journal;
import com.packagename.myapp.model.base.JsonModel;
import com.packagename.myapp.model.base.Paper;
import com.packagename.myapp.model.base.Submission;
import com.packagename.myapp.model.base.User;
import com.packagename.myapp.model.composite.PersonalJournalHistory;
import com.packagename.myapp.model.composite.SubmissionHistory;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Reviewer | Vaadin CRM")
//Child of MainLayout Right side of Main Page
//Which has the path localhost:8080/dashboard
@Route(value = "reviewer", layout = MainLayout.class)
public class ReviewerPage extends VerticalLayout{

    private HashMap<String,User> userData;
    private HashMap<Integer,Paper> paperData;
    private HashMap<Pair<Integer,String>,Submission> submissionData;
    private HashMap<String,Journal> journalData;

    private ResearcherPageController controller;

    public  ReviewerPage() {

    	HorizontalLayout layout = new HorizontalLayout();
    	
    	Button submitReview = new Button("Submit Review",
    			e -> UI.getCurrent().navigate("submit-review"));
    	submitReview.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
    	
    	layout.add(submitReview);
    	
        try {
            userData = JsonModel.getUserData();
            paperData = JsonModel.getPaperData();
            submissionData = JsonModel.getSubmissionData();
            journalData = JsonModel.getJournalData();
        } catch (IOException e1) {
            e1.printStackTrace();
            userData = new HashMap<>();
            paperData = new HashMap<>();
            submissionData = new HashMap<>();
            journalData = new HashMap<>();
        }

        // placeholder value
        String userID = "admin";

        controller = new ResearcherPageController(userData, paperData, submissionData,
                journalData, userID);

        //The first grid.
        H3 first = new H3("Journals");
        Grid<PersonalJournalHistory> journalHistory = new Grid<>();
        journalHistory.addColumn(PersonalJournalHistory::getJName)
                .setHeader("Journal");
        journalHistory.addColumn(PersonalJournalHistory::getField)
                .setHeader("Field");
        journalHistory.addColumn(PersonalJournalHistory::getPapersSubmitted)
                .setAutoWidth(true)
                .setHeader("Papers Submitted");
        journalHistory.addColumn(PersonalJournalHistory::getSubmissionsMade)
                .setAutoWidth(true)
                .setHeader("Submission Made");

        journalHistory.setItems(controller.getPersonalJournalHistory());

        journalHistory.addSelectionListener(e ->{
            PersonalJournalHistory select = e.getFirstSelectedItem().orElse(null);
            if(select == null){
                //error msg
            }else{
                SubmitReview.jName=select.getJName();
            }
        });


        //The second grid.
        H3 third = new H3("Submission History");
        Grid<SubmissionHistory> submissionHistory = new Grid<>();
        submissionHistory.addColumn(SubmissionHistory::getTitle)
                .setHeader("Paper Title");
        submissionHistory.addColumn(SubmissionHistory::getVersion)
                .setWidth("1.2em")
                .setHeader("Version");
        submissionHistory.addColumn(SubmissionHistory::getSubmissionDate)
                .setWidth("5em")
                .setHeader("Submission Date");
        submissionHistory.addColumn(SubmissionHistory::getEditorEmail)
                .setWidth("4.5em")
                .setHeader("Editor");
        submissionHistory.addColumn(SubmissionHistory::getStatus)
                .setHeader("Status");

        submissionHistory.addSelectionListener(e -> {
            SubmissionHistory select = e.getFirstSelectedItem().orElse(null);
            SubmitReview.pName = select.getTitle();
            });

        journalHistory.addSelectionListener(e -> {
            PersonalJournalHistory journal = e.getFirstSelectedItem().orElse(null);
            if (journal == null){
                submissionHistory.setItems(new HashSet<>());
            } else {
                submissionHistory.setItems(journal.getSubmissionHistory());
            }
        });

        //Back button that sends you back to the dashboard.
        Button back = new Button("Back",
                e -> UI.getCurrent().navigate("dashboard"));

        back.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        add(layout,first,journalHistory,third,submissionHistory,back);



    }


}
