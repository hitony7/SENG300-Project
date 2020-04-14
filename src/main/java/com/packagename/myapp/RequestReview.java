package com.packagename.myapp;

import java.io.IOException;
import java.util.ArrayList;

import com.packagename.myapp.model.base.JsonModel;
import com.packagename.myapp.model.base.Paper;
import com.packagename.myapp.model.base.User;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("request-review | Vaadin CRM")
//Child of MainLayout Right side of Main Page
//Which has the path localhost:8080/dashboard
@Route(value = "request-review", layout = MainLayout.class)
public class RequestReview extends VerticalLayout {
	public static String pName= "None";

	public RequestReview() {
		
		H1 header = new H1("Request Review");

		ArrayList<User> userList;
		try{
			userList = new ArrayList<>(JsonModel.getUserData().values());
		} catch(IOException e){
			e.printStackTrace();
			userList = new ArrayList<>();
		}
		
		Select<Paper> selectPaper = new Select<>(); 
		
		
		ArrayList<User> reviewers = new ArrayList<>();
		for(int i = 0; i < userList.size(); i++){
			if(userList.get(i).getUserType().equals("Reviewer")){
				reviewers.add((userList.get(i)));
			}
		}
		//This below grabs the users and filters them by reviewer to put into the grid, but code breaks if 
		//it's in. I commented it out.
		
//		try {
//			reviewers = new ArrayList<>(JsonModel.getUserData().values());
//		} catch(IOException e) {
//			e.printStackTrace();
//			reviewers = new ArrayList<>();
//		}
//		for (User reviewer:reviewers) {
//			if (reviewer.getUserType().equals("Reviewer")) {
//				reviewers.add(reviewer);
//			}
//		}
		
		Label label = new Label("Select Reviewers");
		Grid<User> selectReviewers = new Grid<>();
		selectReviewers.setSelectionMode(SelectionMode.MULTI);
		selectReviewers.setItems(reviewers);
		selectReviewers.addColumn(User::getField).setHeader("Field");
		selectReviewers.addColumn(User::getName).setHeader("Name");
		selectReviewers.addColumn(User::getEmail).setHeader("Email");

		
		TextField setDeadline = new TextField("Set Review Deadline");
		
		Dialog request = new Dialog();
		request.add(new Label("Request Sent"));
		
		Button send = new Button("Send",
				e -> request.open());
		
		Button back = new Button("Back",
				e -> UI.getCurrent().navigate("editor"));
		
		send.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		back.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		
		add(header,selectPaper,label,selectReviewers,setDeadline,send,back);
		
	}
	
}
