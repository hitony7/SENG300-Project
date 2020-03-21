package com.packagename.myapp;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("ManageUser | Vaadin CRM")
//Child of MainLayout Right side of Main Page
//Which has the path localhost:8080/dashboard
@Route(value = "manageUser", layout = MainLayout.class)
public class ManageUser extends VerticalLayout {


	
	public ManageUser() {
		
		setDefaultHorizontalComponentAlignment(Alignment.CENTER);
		//Button to remove user
		Button remove = new Button("Remove User");
		
		//Button to add user
		Button add = new Button("Add User");
		
		//Button to go back.
		Button back = new Button("Back",
				e -> UI.getCurrent().navigate("admin"));
		
		back.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		remove.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		add.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		
		add(remove,add,back);
	}
}
