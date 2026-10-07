package com.gotaigas.views;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.gotaigas.util.Globals;

@Route(value = "decideUserType")
@PageTitle("Decide User Type")
@CssImport("./styles/views/main/main-view.css")
@CssImport("./styles/views/enteridea/enter-idea-view.css")
public class RegistrationDecideUserTypeView extends VerticalLayout {

    private Button student = new Button("Student");
    private Button investor = new Button("Investor");
    private Button back = new Button("Back");


    public RegistrationDecideUserTypeView() {
        
        addClassName("auth-view");
        ThemeHelper.applySavedTheme();
        add(ThemeHelper.createThemeToggleButton());

        H2 header = new H2("Who are you?");
        add(header);

        HorizontalLayout horizontalLayout = new HorizontalLayout();

        student.addThemeVariants(ButtonVariant.LUMO_LARGE);
        investor.addThemeVariants(ButtonVariant.LUMO_LARGE);
        horizontalLayout.add(student);
        horizontalLayout.add(investor);

        add(horizontalLayout);
        add(back);
        
        this.setJustifyContentMode(JustifyContentMode.CENTER);
        this.setAlignItems( Alignment.CENTER );

        student.addClickListener(e -> {
            UI.getCurrent().navigate(Globals.Pages.CREATE_STUDENT_ACCOUNT);
        });

        back.addClickListener(e -> {
            UI.getCurrent().navigate(Globals.Pages.MAIN_VIEW);
        });

        investor.addClickListener(e -> {
            UI.getCurrent().navigate(Globals.Pages.CREATE_INVESTOR_ACCOUNT);
        });
    }

}

