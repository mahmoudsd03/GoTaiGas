package com.gotaigas.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.data.binder.Binder;

import com.gotaigas.control.exception.DatabaseUserException;
import com.gotaigas.dtos.impl.RegistrationDTOImpl;
import com.gotaigas.entities.User;
import com.gotaigas.dtos.RegistrationDTO;
import com.gotaigas.util.Globals;
import com.gotaigas.control.RegistrationControl;
import com.gotaigas.control.RegistrationResult;


@CssImport("./styles/views/main/main-view.css")
//@CssImport("./styles/views/enteridea/enter-idea-view.css")
public abstract class RegistrationView extends VerticalLayout{

    protected FormLayout formLayout;
    private HorizontalLayout buttonLayout;

    private Component theme;

    protected H3 title;

    private TextField firstname = new TextField("First Name");
    private TextField lastname = new TextField("Last Name");
    private TextField email = new TextField("E-Mail");
    private PasswordField password = new PasswordField("Password");
    private DatePicker birthday = new DatePicker("Birthday");

    private Button cancel = new Button("Cancel");
    private Button create = new Button("Create");
    private Button back = new Button("Back");

    protected Binder<RegistrationDTOImpl> binder;

    public RegistrationView() {

        addClassName("auth-view");
        addClassName("enter-idea-view");
        ThemeHelper.applySavedTheme();
        theme = ThemeHelper.createThemeToggleButton();
        add(theme);


        add(createTitle());
        add(createFormLayout());
        add(createButtonLayout());

        this.setJustifyContentMode(JustifyContentMode.CENTER);
        this.setAlignItems(Alignment.CENTER);

        setBinder();

        binder.bindInstanceFields(this);
        clearForm();

        cancel.addClickListener(event -> {

            if (UI.getCurrent().getSession().getAttribute(Globals.CURRENT_USER) == null)
                clearForm();
            else{
                UI.getCurrent().getPage().reload();
            }
        });

        create.addClickListener(e -> {
            RegistrationDTOImpl dto = binder.getBean();
            try{
                System.out.println("Calling registerUser in parentclass: "+this.getClass().getName());
                RegistrationResult result = registerUser(dto);
                if (result.getResult()) {
                    Notification.show("Registrierung erfolgreich");
                    clearForm();
                } else {
                    Notification.show("Fehler: " + result.getReason());
                }
            } catch (DatabaseUserException databaseException) {
                Dialog dialog = new Dialog();
                dialog.add( new Text( databaseException.getReason()) );
                dialog.setWidth("400px");
                dialog.setHeight("150px");
                dialog.open();
            }
        });

        back.addClickListener(e -> {
            UI.getCurrent().navigate(Globals.Pages.DECIDE_USER_TYPE);
        });
    }


    protected abstract void setBinder();
    protected abstract RegistrationResult registerUser(RegistrationDTO dto) throws DatabaseUserException;

    protected abstract void clearForm();

    protected Component createTitle() {
        this.title = new H3("User Registration");
        return title;
    }


    protected Component createFormLayout() {
        formLayout = new FormLayout();
        formLayout.add(firstname, lastname, email, birthday, password);
        return formLayout;
    }

    private Component createButtonLayout() {
        buttonLayout = new HorizontalLayout();
        buttonLayout.addClassName("button-layout");
        create.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(create);
        buttonLayout.add(cancel);
        buttonLayout.add(back);
        return buttonLayout;
    }

    protected void changeProfile (RegistrationControl control, User user) {
        removeClassNames("auth-view","enter-idea-view");
        buttonLayout.remove(create,back,cancel);
        this.remove(title,theme);
        firstname.setValue(user.getFirstName());
        lastname.setValue(user.getLastName());
        email.setValue(user.getEmail());
        email.setReadOnly(true);
        birthday.setValue(user.getDateOfBirth());
        password.setValue(user.getPassword());

        Button save = new Button("Speichern");
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        save.addClickListener(e -> {
            RegistrationDTOImpl dto = binder.getBean();
            try {
                RegistrationResult result = control.update(dto,user.getId());
                if (result.getResult()) {
                    Notification.show("Update erfolgreich");
                } else {
                    Notification.show("Fehler: "+result.getReason());
                }
            }catch (DatabaseUserException databaseException) {
                Dialog dialog = new Dialog();
                dialog.add( new Text( databaseException.getReason()) );
                dialog.setWidth("400px");
                dialog.setHeight("150px");
                dialog.open();
            }
        });
        buttonLayout.add(save,cancel);
    }



}

