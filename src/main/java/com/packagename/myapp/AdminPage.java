
package com.packagename.myapp;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;


@PageTitle("Administrator | Vaadin CRM")
//Child of MainLayout Right side of Main Page
//Which has the path localhost:8080/dashboard
@Route(value = "admin", layout = MainLayout.class)
public class AdminPage extends VerticalLayout {
	
	public AdminPage() {
		setDefaultHorizontalComponentAlignment(Alignment.CENTER);
		//Welcome the administrator
		H2 welcomeText = new H2("Welcome, administrator.");
		
		//Button to allow the administrator to edit users
		Button manageUser = new Button("User Management", 
				e -> UI.getCurrent().navigate("manageUser"));
		
		//Administrator settings (The page looked so bland.)
		Button settings = new Button("Administrator Settings");
				
		//Go back to dashboard
		Button back = new Button("Back",
				e -> UI.getCurrent().navigate(""));
		
		manageUser.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		settings.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		back.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		
	
		add(welcomeText,manageUser,settings,back);
		
		
	}
}