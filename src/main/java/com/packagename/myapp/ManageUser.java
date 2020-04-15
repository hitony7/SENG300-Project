package com.packagename.myapp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import com.packagename.myapp.control.ManageUserController;
import com.packagename.myapp.model.base.JsonModel;
import com.packagename.myapp.model.base.User;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;


@PageTitle("ManageUser | Vaadin CRM")
//Child of MainLayout Right side of Main Page
//Which has the path localhost:8080/dashboard
@Route(value = "manageUser", layout = MainLayout.class)
public class ManageUser extends VerticalLayout {

	public ManageUser() {

		Binder<User> binder = new Binder(ManageUserController.class);
		H5 UserManagement = new H5("User Management");
		add(UserManagement);
		//Make a form layout for the adding user
		FormLayout form = new FormLayout();

		//create the column for the display
		form.setResponsiveSteps(
				new ResponsiveStep("20em",1),
				new ResponsiveStep("25em",2),
				new ResponsiveStep("35em",3),
				new ResponsiveStep("50em", 4),
				new ResponsiveStep("65em", 5),
				new ResponsiveStep("80em", 6));

		//create text field for the name input
		TextField userName = new TextField();
		userName.setLabel("Name");

		//creates the binder to get data easier from the user.json file
		binder.forField(userName)
				.asRequired()
				.bind(User::getName, User::setName);


		//textfield for password
		TextField password = new TextField();
		password.setLabel("Password");

		binder.forField(password)
				.asRequired()
				.bind(User::getPassword, User::setPassword);
		
		
		//select drop down for the different types
		Select <String> userType = new Select();
		userType.setLabel("User Type");
		userType.setValue("Editor");
		userType.setItems("Editor","Reviewer","Researcher","Admin");

		binder.forField(userType)
				.asRequired()
				.bind(User::getUserType, User::setUserType);

		//text field for the email input
		TextField email = new TextField();
		email.setLabel("Email");

		binder.forField(email)
				.asRequired()
				.bind(User::getEmail, User::setEmail);

		//text field for the user id input
		TextField UserId = new TextField();
		UserId.setLabel("UserId");

		binder.forField(UserId)
				.asRequired()
				.bind(User::getUserID, User::setUserID);

		//textfield for the field of the user works in
		TextField field = new TextField();
		field.setLabel("field");

		binder.forField(field)
				.asRequired()
				.bind(User::getField, User::setField);
		
		form.add(UserId,userName,password,userType, field,email);
		
		
		//Add dialog
		Dialog removed = new Dialog();
		Dialog added = new Dialog();
		Dialog edited = new Dialog();

		//Prompt
		removed.add(new Label("Selected user have been removed."));
		added.add(new Label("Added selected user."));
		edited.add(new Label("Selected user have been edited"));


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
		userGrid.addColumn(User::getName).setHeader("Name");
		userGrid.addColumn(User::getPassword).setHeader("Password");
		userGrid.addColumn(User::getUserType).setHeader("User Type");
		userGrid.addColumn(User::getField).setHeader("Field");
		userGrid.addColumn(User::getEmail).setHeader("User Email");

		//Allow admin to select multiple objects from the grid
		userGrid.setSelectionMode(SelectionMode.SINGLE);

		userGrid.addSelectionListener(e ->{
			User select = e.getFirstSelectedItem().orElse(null);
			if(select == null){
				select = new User(null, null, null, null, null, null);
			}else{
				binder.readBean(select);
			}
		});


		//Label for adding new users.
		H5 newUser = new H5("Add New User");

		//Button to go back.
		Button back = new Button("Back",
				e -> UI.getCurrent().navigate("admin"));

		//Button to add a user and rund the code to add the user.
		Button add = new Button("Add User", e -> {
			if(UserId.getValue() == null || userName.getValue() == null || password.getValue() == null
			|| field.getValue() == null || ((String)userType.getValue()) == null || email.getValue() == null){
				Dialog invalid = new Dialog();
				invalid.add(new Label("Please fill out all of the fields"));
				invalid.open();
				userGrid.getDataProvider().refreshAll();
			}
			else{
				HashMap<String, User> addNewUser = new HashMap<String, User>();
				User temp = new User(UserId.getValue(), password.getValue(), userName.getValue(), email.getValue(), field.getValue(), (String) userType.getValue());
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

		//Edit the user data
		ArrayList<User> finalUserList = userList;
		Button edit =  new Button("Edit User", e ->{
			Set<User> select = userGrid.getSelectedItems();
			User[] selectedInfo = new User [1];
			select.toArray(selectedInfo);

			try {
				binder.writeBean(selectedInfo[0]);
				HashMap <String, User> selected = new HashMap<String, User>();
				for(User temp: finalUserList){
					selected.put(temp.getUserID(), temp);
				}
				try {
					JsonModel.setUserData(selected);
				}catch(IOException e5){
					e5.printStackTrace();
				}
			}catch(ValidationException e4){
				e4.printStackTrace();
			}
			edited.open();
			userGrid.getDataProvider().refreshAll();
		});

		//Button to remove a user.
		Button remove = new Button("Remove Selected User(s)", e ->{
			//Grabs all of the user data in the user.json file and put it in a Hashmap
			HashMap <String, User> allUser;
			try{
				allUser = JsonModel.getUserData();
			}catch(IOException error){
				error.printStackTrace();
				allUser = new HashMap<>();
			}

			Set<User> select = userGrid.getSelectedItems();
			User[] selectedInfo = new User [1];
			select.toArray(selectedInfo);

			allUser.remove(selectedInfo[0].getUserID());

			try {
				JsonModel.setUserData(allUser);
			} catch(IOException e3){
				e3.printStackTrace();
			}
			removed.open();
			UI.getCurrent().getPage().reload();
		});


		//Set the buttons to the left side of the page.
		setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.START);



		back.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

		//add the grid, remove button, add button, edit button, and back button
		add(userGrid,remove,form,add, edit, back);
	}




}