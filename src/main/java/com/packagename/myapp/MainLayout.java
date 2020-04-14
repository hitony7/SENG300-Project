package com.packagename.myapp;


import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLink;

//Parent Class for Page Left side and Header Bar
@CssImport("./styles/shared-styles.css")
//Which has the path localhost:8080/dashboard
public class MainLayout extends AppLayout {
	
	
	
    //Constructor of Two components
    public MainLayout() {
        createHeader();
        createDrawer();
        
        
    }

    //Top Bar the header
    private void createHeader() {
        H1 logo = new H1("Welcome to ripoff D2L");  // h1 HEADER LIKE html
        logo.addClassName("logo");

        HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), logo);
        header.addClassName("header");   //HTML
        header.setWidth("100%");   //SCALE TO WINDOW SIZE
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);

        Button jButton = new Button("Journals",
                e -> UI.getCurrent().navigate("submit"));
        jButton.setWidth("300px");
        Button resButton = new Button("Researchers",
                e -> UI.getCurrent().navigate("researcher"));
        resButton.setWidth("300px");
        Button revButton = new Button("Reviewers",
                e -> UI.getCurrent().navigate("submit"));
        revButton.setWidth("300px");
        Button eButton = new Button("Editors",
                e -> UI.getCurrent().navigate("editor"));
        eButton.setWidth("300px");
        
        //Button to go to the submit page
        Button submitPage = new Button("Create Submission",
        		e -> UI.getCurrent().navigate("submit"));
        submitPage.setWidth("500px");
        
        //Button to go to the resubmit page
        Button resubmitPage = new Button("Resubmit a Journal",
        		e -> UI.getCurrent().navigate("resubmit"));
        resubmitPage.setWidth("500px");
        
        
        //Button needs to be repositioned
        Button logoutB = new Button("Logout",
                e -> UI.getCurrent().navigate(""));
        logoutB.setWidth("220px");

        addToNavbar(header);
        addToNavbar(submitPage);
        addToNavbar(resubmitPage);
        addToNavbar(jButton,resButton,revButton,eButton);

        addToNavbar(logoutB);
    }
    //This is the Left Side Bar
    private void createDrawer() {
        //Vertical style List
        addToDrawer(new VerticalLayout(
                new RouterLink("Dashboard", DashboardView.class)

        ));
    }


}