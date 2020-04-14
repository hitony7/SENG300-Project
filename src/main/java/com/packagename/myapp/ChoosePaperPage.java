package com.packagename.myapp;
import com.packagename.myapp.model.base.Paper;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Reviewer | Vaadin CRM")
//Child of MainLayout Right side of Main Page
//Which has the path localhost:8080/dashboard
@Route(value = "choose-paper", layout = MainLayout.class)
public class ChoosePaperPage extends VerticalLayout {
	
	public ChoosePaperPage() {
		
		Dialog popup = new Dialog();
		popup.add(new Label("Paper Set."));
		
		H1 header = new H1("Choose Paper");
		
		Label label = new Label("Select Paper");
		Select<Paper> papers = new Select<>();
		
		Button set = new Button("Set", 
				e -> popup.open());
		Button back = new Button("Back",
				e -> UI.getCurrent().navigate("editor"));
		
		set.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		back.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		
		add(header,label,papers,set,back);
	}
	
}
