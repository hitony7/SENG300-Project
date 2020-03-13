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
        //Button needs to be repositioned
        Button logoutB = new Button("Logout",
                e -> UI.getCurrent().navigate(""));

        addToNavbar(logoutB);
        addToNavbar(header);
    }
    //This is the Left Side Bar
    private void createDrawer() {
        //Vertical style List
        addToDrawer(new VerticalLayout(
                new RouterLink("Dashboard", DashboardView.class)
        ));
    }


}