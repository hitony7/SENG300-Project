package com.packagename.myapp;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

import com.packagename.myapp.control.ManageUserController;
import com.packagename.myapp.model.JsonModel;
import com.vaadin.flow.component.ClickEvent;
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
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang3.ObjectUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.*;


@PageTitle("ManageUser | Vaadin CRM")
//Child of MainLayout Right side of Main Page
//Which has the path localhost:8080/dashboard
@Route(value = "manageUser", layout = MainLayout.class)
public class ManageUser extends VerticalLayout {

	static ArrayList<String> data = new ArrayList<>();
	static ArrayList<String> data2 = new ArrayList<>();
	public ManageUser() {
		
		Binder<ManageUserController> binder = new Binder(ManageUserController.class);

		//Make a form layout for the adding user
		FormLayout form = new FormLayout();
		
		form.setResponsiveSteps(
				new ResponsiveStep("25em",1),
				new ResponsiveStep("32em",2),
				new ResponsiveStep("40em",3));
		
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
		
		form.add(userName,password,userType);
		
		
		//Add dialog
		Dialog removed = new Dialog();
		Dialog added = new Dialog();

		//Prompt
		removed.add(new Label("Selected users have been removed."));

		//Prompt
		added.add(new Label("Added selected user."));
		
		
		//JSON Parser object to parse read file
		JSONParser jsonParser = new JSONParser();

		ArrayList<Users> userList = new ArrayList<Users>();

		try(FileReader reader = new FileReader("users.json")) {
			Object obj = jsonParser.parse(reader);
			JSONArray userArray = (JSONArray) obj;

			userArray.forEach(emp -> parseEmployeeObject( (JSONObject) emp));


			//userList.add(temp);
		} catch(FileNotFoundException e){
			e.printStackTrace();
		} catch(IOException e){
			e.printStackTrace();
		} catch(ParseException e){
			e.printStackTrace();
		}

		for(int i = 0; i < data.size(); i+=3) {
			Users temp = new Users(data.get(i), data.get(i+1), data.get(i+2));
			userList.add(temp);
		}
		data.clear();

		//Create new grid
		Grid<Users> userGrid = new Grid<>();

		//Add the userList items to the grid.
		userGrid.setItems(userList);

		//Add columns to the grid
		userGrid.addColumn(Users::getName).setHeader("Username");
		userGrid.addColumn(Users::getPassword).setHeader("Password");
		userGrid.addColumn(Users::getUsertype).setHeader("User Type");

		//Allow admin to select multiple objects from the grid
		userGrid.setSelectionMode(SelectionMode.SINGLE);
		

		//Label for adding new users.
		H5 newUser = new H5("Add New User");

		//Button to go back.
		Button back = new Button("Back",
				e -> UI.getCurrent().navigate("admin"));

		//Button to add a user.
		Button add = new Button("Add User", e -> {
			System.out.println(userType.getValue());
		});



		//Button to remove a user.
		Button remove = new Button("Remove Selected User(s)",
				e -> grabbing());


		//Set the buttons to the left side of the page.
		setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.START);



		back.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

		add(userGrid,remove,form,add,back);
	}

	private static void parseEmployeeObject(JSONObject employee) {
		//Get employee object within list
		JSONObject employeeObject = (JSONObject) employee.get("user");

		//Get employee first name
		String username = (String) employeeObject.get("username");
		String password = (String) employeeObject.get("password");
		String usertype = (String) employeeObject.get("usertype");
		//return string

		storeInfo(username, password, usertype);

	}

	private static void storeInfo(String u, String p, String t){
		data.add(u);
		data.add(p);
		data.add(t);
	}

	//TODO check if the type if not null and fix that part
	private static void grab(String newUser, String newPass){
		if(newUser.equals(null) || newPass.equals(null) ) {
			Dialog invalid = new Dialog();
			invalid.add(new Label("Please fill out all of the fields"));
			invalid.open();
		}else{
			JsonModel a = new JsonModel();
			a.newUser(newUser,  newPass,  "");

			Dialog done = new Dialog();
			done.add(new Label("New User Added"));
			done.open();
			UI.getCurrent().getPage().reload();
		}
	}

	private static void grabbing(){
		String delUser  = "kevinasd";

		JSONArray old = new JSONArray();

		JsonModel b = new JsonModel();
		old = b.readOLD("users.json");

		old.forEach(e2  ->  graball((JSONObject) e2));

		for(int i = 0; i < data2.size(); i+=3){
			if(delUser.equals(data2.get(i))) {
				data2.remove(i);
				data2.remove(i + 1);
				data2.remove(i + 2);
			}
		}

		for(int i = 0; i < data2.size(); i+=3){
			b.newUser(data2.get(i), data2.get(i+1), data2.get(i+2));
		}
		data2.clear();

		Dialog done2 = new Dialog();
		done2.add(new Label("Removed user"));
		done2.open();
		UI.getCurrent().getPage().reload();

	}

	private static void graball(JSONObject input){
		//JSONObject olduser = (JSONObject) input.get("users");

		String u = (String) input.get("username");
		String p = (String) input.get("password");
		String t = (String) input.get("usertype");

		storeold(u,p,t);


	}

	private static void storeold(String u, String p, String t){
		data2.add(u);
		data2.add(p);
		data2.add(t);
	}
}