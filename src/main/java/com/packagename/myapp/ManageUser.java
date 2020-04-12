package com.packagename.myapp;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

import com.packagename.myapp.control.ManageUserController;
import com.packagename.myapp.model.JsonModel;
import com.packagename.myapp.model.User;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.charts.model.Dial;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;


import java.io.FileReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import elemental.json.Json;
import org.apache.commons.lang3.ObjectUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.print.attribute.standard.MediaPrintableArea;
import javax.swing.*;


@PageTitle("ManageUser | Vaadin CRM")
//Child of MainLayout Right side of Main Page
//Which has the path localhost:8080/dashboard
@Route(value = "manageUser", layout = MainLayout.class)
public class ManageUser extends VerticalLayout {

	public ManageUser() {
		
		Binder<ManageUserController> binder = new Binder(ManageUserController.class);

		//Make a form layout for the adding user
		FormLayout form = new FormLayout();
		
		form.setResponsiveSteps(
				new ResponsiveStep("20em",1),
				new ResponsiveStep("25em",2),
				new ResponsiveStep("35em",3),
				new ResponsiveStep("50em", 4),
				new ResponsiveStep("65em", 5),
				new ResponsiveStep("80em", 6));
		
		TextField userName = new TextField();
		userName.setLabel("Username");
		
		binder.forField(userName)
				.asRequired()
				.bind(ManageUserController::getUserName, ManageUserController::setUserName);
		
		
		TextField password = new TextField();
		password.setLabel("Password");
		
		
		
		Select userType = new Select();
		userType.setLabel("User Type");
		userType.setValue("Editor");
		userType.setItems("Editor","Reviewer","Researcher");

		TextField email = new TextField();
		email.setLabel("Email");

		TextField UserId = new TextField();
		UserId.setLabel("UserId");

		TextField field = new TextField();
		field.setLabel("field");
		
		form.add(UserId,userName,password,userType, field,email);
		
		
		//Add dialog
		Dialog removed = new Dialog();
		Dialog added = new Dialog();
		Dialog edited = new Dialog();

		//Prompt
		removed.add(new Label("Selected user have been removed."));
		added.add(new Label("Added selected user."));
		edited.add(new Label("Selected user have been edited"));

//		HashMap<String, User> firstUser = new HashMap<String, User>();
//		User temp = new User("Admin", "Pass123", "Admin", "Admin@gmail.com", "Admin", "Admin");
//		firstUser.put("Admin",temp);
//		try {
//			JsonModel.setUserData(firstUser);
//		}catch(IOException e){
//			e.printStackTrace();
//		}

		ArrayList<User> userList;
		try{
			userList = new ArrayList<>(JsonModel.getUserData().values());
		} catch(IOException e){
			e.printStackTrace();
			userList = new ArrayList<>();
		}

		//Create new grid
		Grid<User> userGrid = new Grid<>();

		//Add the userList items to the grid.
		userGrid.setItems(userList);

		//Add columns to the grid
		userGrid.addColumn(User::getUserID).setHeader("UserID");
		userGrid.addColumn(User::getName).setHeader("Username");
		userGrid.addColumn(User::getPassword).setHeader("Password");
		userGrid.addColumn(User::getUserType).setHeader("User Type");
		userGrid.addColumn(User::getField).setHeader("Field");
		userGrid.addColumn(User::getEmail).setHeader("User Email");

		//Allow admin to select multiple objects from the grid
		userGrid.setSelectionMode(SelectionMode.SINGLE);



		//Label for adding new users.
		H5 newUser = new H5("Add New User");

		//Button to go back.
		Button back = new Button("Back",
				e -> UI.getCurrent().navigate("admin"));

		//Button to add a user.
		Button add = new Button("Add User", e -> {
			if(UserId.getValue().equals(null) || userName.getValue().equals(null) || password.getValue().equals(null)
			|| userType.getValue().equals(null) || field.getValue().equals(null) || email.getValue().equals(null)){
				Dialog invalid = new Dialog();
				invalid.add(new Label("Please fill out all of the fields"));
				invalid.open();
				UI.getCurrent().getPage().reload();
			}
			else{
				HashMap<String, User> addNewUser = new HashMap<String, User>();
				User temp = new User(UserId.getValue(), userName.getValue(), password.getValue(), (String) userType.getValue(), field.getValue(), email.getValue());
				try {
					addNewUser = JsonModel.getUserData();
				} catch(IOException e2){
					e2.printStackTrace();
				}

				addNewUser.put(UserId.getValue(),temp);
				try {
					JsonModel.setUserData(addNewUser);
				}catch(IOException e1){
					e1.printStackTrace();
				}
				added.open();
				UI.getCurrent().getPage().reload();
			}
		});

		Button edit =  new Button("Edit User");

		//Button to remove a user.
		Button remove = new Button("Remove Selected User(s)");


		//Set the buttons to the left side of the page.
		setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.START);



		back.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

		add(userGrid,remove,form,add, edit, back);
	}




}