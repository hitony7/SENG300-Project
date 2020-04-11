package com.packagename.myapp;

import com.packagename.myapp.model.JsonModel;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
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

    public LoginView() {
        // Use TextField for standard text input
        TextField textField = new TextField("Username");
        PasswordField passwordField = new PasswordField("Password");
        passwordField.setLabel("Password");
        passwordField.setPlaceholder("Enter password");
        passwordField.setValue("secret1");
        // Button click listeners can be defined as lambda expressions

        GreetService greetService = new GreetService();
        Button button = new Button("Say hello my dudes",
                e -> jsonReader.newUser(textField.getValue(),textField2.getValue(),"admin"));

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
