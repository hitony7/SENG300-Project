package com.packagename.myapp;


import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import javax.swing.*;


@PageTitle("Dashboard | Vaadin CRM")
// Child of MainLayout Right side of Main Page
//Which has the path localhost:8080/dashboard
@Route(value = "dashboard", layout = MainLayout.class)
public class DashboardView extends VerticalLayout {
    public DashboardView() {


        addClassName("dashboard-view");  //FOR CSS REF
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        Label gap = new Label();
        gap.setHeight("You can use Labels");
        add(gap);


        H1 logo = new H1("Welcome to the MainPage");
        
        //Button to go to the reviewer page
        Button researcher = new Button("To researcher page",
        		e -> UI.getCurrent().navigate("researcher"));
        
        Button reviewer = new Button("To reviewer page",
        		e -> UI.getCurrent().navigate("reviewer"));
        
        Button editor = new Button("To editor page",
        		e -> UI.getCurrent().navigate("editor"));

        researcher.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        reviewer.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        editor.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        
        
        add(logo,researcher,reviewer,editor);
        
        
      
        
    }

}

