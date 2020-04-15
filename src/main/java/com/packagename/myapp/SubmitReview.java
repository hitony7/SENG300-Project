package com.packagename.myapp;

import com.packagename.myapp.model.base.Paper;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("submit-review| Vaadin CRM")
//Child of MainLayout Right side of Main Page
//Which has the path localhost:8080/dashboard
@Route(value = "submit-review", layout = MainLayout.class)
public class SubmitReview extends VerticalLayout {
	public static String jName ="none";
	public static String pName = "none";
	public SubmitReview() {

		//header
		H1 header = new H1("Submit Review");
		H4 subheader = new H4("You have selected " + jName + "(journal)");
		Label selectPaper = new Label("Select Paper");
		
		//Select<Paper> paperSelect = new Select<>();
		TextField text = new TextField();
		text.setReadOnly(true);
		text.setValue(pName);

		//text area for comment
		TextArea comment = new TextArea("Comment");
		comment.setPlaceholder("Comments");
		comment.getStyle().set("minHeight","150px");
		comment.getStyle().set("minWidth", "50%");
		
		//Has errors, no idea how to fix it.
//		MemoryBuffer buffer = new MemoryBuffer();
//        Upload upload = new Upload(buffer);
//
//        upload.addSucceededListener(event -> {
//            Component component = createComponent(event.getMIMEType(),
//                    event.getFileName(), buffer.getInputStream());
//            showOutput(event.getFileName(), component, output);
//        });
		
		Dialog popup = new Dialog();
		popup.add(new Label("Review Submitted"));
		
		Button send = new Button("Send",
				e ->{
					popup.open();

				});
		
		Button back = new Button("Back",
				e -> UI.getCurrent().navigate("reviewer"));
		
		back.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		send.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		
		add(header,subheader,selectPaper,text,comment,send,back);
		
	}
	
}
