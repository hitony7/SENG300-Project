package com.packagename.myapp;

import com.packagename.myapp.control.ManageUserController;
import com.packagename.myapp.model.JsonModel;
import com.packagename.myapp.model.Paper;
import com.packagename.myapp.model.User;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;

import java.io.IOException;
import java.util.HashMap;


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
        Binder<ManageUserController> binder = new Binder(ManageUserController.class);
        try {
            userList = JsonModel.getUserData() ;
            System.out.println("Loaded");

      } catch (IOException e1) {

          e1.printStackTrace();
           userList = new HashMap<>();
        }
        // Use TextField for standard text input
        TextField textField = new TextField("Username");
        PasswordField passwordField = new PasswordField("Password");
        passwordField.setPlaceholder("Enter password");
        passwordField.setValue("secret1");
        // Button click listeners can be defined as lambda expressions

        GreetService greetService = new GreetService();
        Button button = new Button("Login",
                e -> {
                    User user = ManageUserController.validateLogin(userList, textField.getValue(), passwordField.getValue());
                    if(user != null){
                        Dialog logSucc = new Dialog();

                        //Prompt
                        logSucc.add(new Label("Login Sucessful"  +  "\n" + "Welcome:"  + user.getUserID()) + " (" + user.getUserType() + " )" );
                        logSucc.open();
                        //Logic for what type of user
                        UI.getCurrent().navigate("dashboard");
                        System.out.println("Login SUCESSFUL");

                        //Logic for what type of user
                    } else   {
                        System.out.println("WRONG USERNAME/PASSWORD");
                        Dialog logFail = new Dialog();

                        //Prompt
                        logFail.add(new Label("Invaild Username and/or Password."));
                        logFail.open();
                    }
                    //jsonReader.checkUserPass();
                    //jsonReader.newUser(textField.getValue(), passwordField.getValue(), "admin");
                });

        Button newpage = new Button("Click this to go to main page (This is after login)",
                e -> UI.getCurrent().navigate("dashboard"));

        Button adminPage = new Button("Administrator",
        		e -> UI.getCurrent().navigate("admin"));


        // Theme variants give you predefined extra styles for components.
        // Example: Primary button is more prominent look.
        button.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        // You can specify keyboard shortcuts for buttons.
        // Example: Pressing enter in this view clicks the Button.
        button.addClickShortcut(Key.ENTER);

        // Use custom CSS classes to apply styling. This is defined in shared-styles.css.
        addClassName("centered-content");

        add(textField, passwordField, button, newpage, adminPage);
    }


}
