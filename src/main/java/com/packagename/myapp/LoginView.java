package com.packagename.myapp;

import java.io.IOException;
import java.util.HashMap;

import com.packagename.myapp.control.ManageUserController;
import com.packagename.myapp.model.base.JsonModel;
import com.packagename.myapp.model.base.User;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;


/**
 * The main view contains a button and a click listener.
 */
@Route("") //This is the First Page you see each page with have a view(page)
// @PWA(name = "Project Base for Vaadin", shortName = "Project Base")
@CssImport("./styles/shared-styles.css")
@CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")
public class LoginView extends VerticalLayout  {
    JsonModel jsonReader = new JsonModel();
    private HashMap<String, User> userList;

    public LoginView() {
        // create heading for the paper
        H1 title = new H1("Welcome to the University of Winnipeg");
        H2 project= new H2("Journal Login System.");
        Binder<ManageUserController> binder = new Binder(ManageUserController.class);
        try {
            userList = JsonModel.getUserData() ;
            System.out.println("Loaded");
        } catch (IOException e1) {
          e1.printStackTrace();
           userList = new HashMap<>();
        }
        // Button click listeners can be defined as lambda expressions
        LoginForm logincomponent = new LoginForm();
        logincomponent.addLoginListener(e -> {
            User user = ManageUserController.validateLogin(userList, e.getUsername(), e.getPassword());
            if(user != null){
                Dialog logSucc = new Dialog();
                //Prompt
                logSucc.add(new Label("Login Sucessful"  +  "\n" + "Welcome:"  + user.getUserID() + " (" + user.getUserType() + ")"));
                logSucc.open();
                //Logic for what type of user
                if(user.getUserType().equals("Admin")){
                    new SessionUser(user);
                    UI.getCurrent().navigate("admin");
                }
                else if(user.getUserType().equals("Reviewer")){
                    new SessionUser(user);
                    UI.getCurrent().navigate("reviewer");

                }
                else if(user.getUserType().equals("Researcher")){
                    new SessionUser(user);
                    UI.getCurrent().navigate("researcher");
                }
                 else if(user.getUserType().equals("Editor")){
                    new SessionUser(user);
                    UI.getCurrent().navigate("editor");
                } else  {
                    new SessionUser(user);
                    UI.getCurrent().navigate("dashboard");
                    System.out.println("Login SUCESSFUL");
                }


                //Logic for what type of user
            } else   {
                System.out.println("WRONG USERNAME/PASSWORD");
                Dialog logFail = new Dialog();

                //Prompt
                logFail.add(new Label("Invaild Username and/or Password."));
                logFail.open();
            }
            logincomponent.setEnabled(true);
        });


        Button newpage = new Button("Click this to go to main page (This is after login)",
                e -> UI.getCurrent().navigate("dashboard"));

        Button adminPage = new Button("Administrator",
        		e -> UI.getCurrent().navigate("admin"));


        // Theme variants give you predefined extra styles for components.
        // Example: Primary button is more prominent look.
        //button.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        // You can specify keyboard shortcuts for buttons.
        // Example: Pressing enter in this view clicks the Button.
        //button.addClickShortcut(Key.ENTER);

        // Use custom CSS classes to apply styling. This is defined in shared-styles.css.
        addClassName("centered-content");
        //setHorizontalComponentAlignment( logincomponent,);
        add(title,project, logincomponent);
        add(newpage, adminPage);
    }


}