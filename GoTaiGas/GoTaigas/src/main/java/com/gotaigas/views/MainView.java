package com.gotaigas.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;

import com.gotaigas.control.LoginControl;
import com.gotaigas.control.exception.DatabaseUserException;
import com.gotaigas.entities.User;
import com.gotaigas.util.Globals;
import com.gotaigas.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.dependency.CssImport;

/**
 * View zur Darstellung der Startseite. Diese zeigt dem Benutzer ein Login-Formular an.
 * ToDo: Integration einer Seite zur Registrierung von Benutzern
 */

@Route(value = "" )
@RouteAlias(value = "login")
@CssImport("./styles/views/main/main-view.css")
public class MainView extends VerticalLayout {

    // Default: Einfügung des LoginControl-Objekts als Singleton (Spring-Annotation)
    // Frage: Gibt es hier vielleicht Probleme ... ?
    @Autowired
    private LoginControl loginControl;

    public MainView() {
        setSizeFull();


        addClassName("auth-view");
        ThemeHelper.applySavedTheme();

        add(createLogInForm());
        add(ThemeHelper.createThemeToggleButton());


        this.setPadding(true);
        this.setJustifyContentMode(JustifyContentMode.CENTER);
        this.setAlignItems( Alignment.CENTER );
    }

    private Component createLogInForm () {
        LoginI18n settings = LoginI18n.createDefault();
        LoginI18n.Form form = settings.getForm();
        form.setUsername("Email");
        form.setForgotPassword("Registration");
        settings.setForm(form);

        LoginI18n.ErrorMessage errorMessage = settings.getErrorMessage();
        errorMessage.setTitle("Login Failed");
        errorMessage.setMessage("email and/or password are incorrect, please try again");
        errorMessage.setUsername("Email is required");
        settings.setErrorMessage(errorMessage);


        LoginForm component = new LoginForm();
        component.setI18n(settings);

        component.addLoginListener(e -> {

            boolean isAuthenticated = false;
            try {
                isAuthenticated = loginControl.authentificate( e.getUsername() , e.getPassword() );

            } catch (DatabaseUserException databaseException) {
                Dialog dialog = new Dialog();
                dialog.add( new Text( databaseException.getReason()) );
                dialog.setWidth("400px");
                dialog.setHeight("150px");
                dialog.open();
            }
            if (isAuthenticated) {
                grabAndSetUserIntoSession();
                navigateToMainPage();

            } else {
                // Kann noch optimiert werden
                component.setError(true);
            }
        });

        component.addForgotPasswordListener(e -> {
            navigateToDecideUserTypePage();
        });


        return component;

    }

    private void grabAndSetUserIntoSession() {
        User userDTO = loginControl.getCurrentUser();
        Utils.setCurrentUser(userDTO);
    }


    private void navigateToDecideUserTypePage () {
        UI.getCurrent().navigate(Globals.Pages.DECIDE_USER_TYPE);
    }

    private void navigateToMainPage() {
        UI.getCurrent().navigate(Globals.Pages.SHOW_IDEAS);
    }
}
