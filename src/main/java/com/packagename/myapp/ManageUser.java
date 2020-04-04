package com.packagename.myapp;

import java.util.ArrayList;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
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
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("ManageUser | Vaadin CRM")
//Child of MainLayout Right side of Main Page
//Which has the path localhost:8080/dashboard
@Route(value = "manageUser", layout = MainLayout.class)
public class ManageUser extends VerticalLayout {


	
	public ManageUser() {
		
		//Add dialog
		Dialog removed = new Dialog();
		Dialog added = new Dialog();
		
		//Prompt
		removed.add(new Label("Selected users have been removed."));
		
		//Prompt
		added.add(new Label("Added selected user."));
		
		Select<String> addUser = new Select<>();
		
		//Put these objects here to test the grid. We can remove them afterwards when we implement database stuff.
		Users reviewer = new Users("Jane Doe","janedoe123","reviewer");
		Users editor = new Users("John Doe","notjohn123", "editor");
		Users researcher = new Users("Joan D'arc","joandarc123","researcher");
		
		//ArrayList to hold the temporary objects
		ArrayList<Users> userList = new ArrayList<Users>(); 
		userList.add(reviewer);
		userList.add(editor);
		userList.add(researcher);
		
		//Create new grid
		Grid<Users> userGrid = new Grid<>();
		
		//Add the userList items to the grid.
		userGrid.setItems(userList);
		
		//Add columns to the grid
		userGrid.addColumn(Users::getName).setHeader("Username");
		userGrid.addColumn(Users::getPassword).setHeader("Password");
		userGrid.addColumn(Users::getUsertype).setHeader("User Type");
		
		//Allow admin to select multiple objects from the grid
		userGrid.setSelectionMode(SelectionMode.MULTI);
		
		//Label for adding new users.
		H5 newUser = new H5("Add New User");
		
		//Button to go back.
		Button back = new Button("Back",
				e -> UI.getCurrent().navigate("admin"));
		
		//Button to add a user.
		Button add = new Button("Add User",
				e -> added.open());
		
		//Button to remove a user.
		Button remove = new Button("Remove Selected User(s)", 
				e -> removed.open());
		
		//Label to select user
		addUser.setLabel("Select User");
		//Temporary placeholders
		addUser.setItems("Adam","Eve");
		
		
		//Set the buttons to the left side of the page.
		setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.START);
		
		
		
		back.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		
		add(userGrid,remove,newUser,addUser,add,back);
	}
}