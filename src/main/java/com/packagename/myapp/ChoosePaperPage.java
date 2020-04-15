package com.packagename.myapp;
import com.packagename.myapp.model.base.Paper;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

@PageTitle("Reviewer | Vaadin CRM")
//Child of MainLayout Right side of Main Page
//Which has the path localhost:8080/dashboard
@Route(value = "choose-paper", layout = MainLayout.class)
public class ChoosePaperPage extends VerticalLayout {
	public static String pName ="none";
	public ChoosePaperPage() {
		//creates pop up dialog for when the paper is set
		Dialog popup = new Dialog();
		popup.add(new Label("Paper Set."));

		//creates header for the page
		H1 header = new H1("Choose Paper");

		//sets a textfield for the selected paper
		Label label = new Label("Select Paper");
		//Select<Paper> papers = new Select<>();
		TextField text = new TextField();
		text.setReadOnly(true);
		text.setValue(pName);
		Button set = new Button("Set", 
				e -> popup.open());


		Anchor download = new Anchor(new StreamResource(pName+ ".PDF" , () -> {

			try {
				return createResource();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			return null;
		}), "");
		download.getElement().setAttribute("download", true);
		download.add(new Button(new Icon(VaadinIcon.DOWNLOAD_ALT)));

		//back button
		Button back = new Button("Back",
				e -> UI.getCurrent().navigate("editor"));
		
		set.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		back.addThemeVariants(ButtonVariant.LUMO_PRIMARY);


		
		add(header,label,text,download,set,back);

	}

	//method to create the file path
	private InputStream createResource() throws FileNotFoundException {
		InputStream inputstream = new FileInputStream("data\\journals\\Science\\stamps.def");
		return  inputstream;
	}

}
