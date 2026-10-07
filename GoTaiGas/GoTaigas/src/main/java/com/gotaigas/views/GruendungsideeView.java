package com.gotaigas.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.component.notification.Notification;

import com.gotaigas.control.ChatControl;
import com.gotaigas.control.GruendungsideeControl;
import com.gotaigas.control.LoginControl;
import com.gotaigas.dtos.impl.GruendungsideeDTOImpl;
import com.gotaigas.entities.User;

import com.gotaigas.util.Globals;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.gotaigas.repository.StudentRepository;


@Route(value = Globals.Pages.CREATE_IDEA, layout = AppView.class)
@PageTitle("Gründungsidee erstellen")
public class GruendungsideeView extends TaigaView implements BeforeEnterObserver {
    private final StudentRepository studentRepository;

    private TextField titel = new TextField("Titel");
    private TextField kategorie = new TextField("Kategorie");
    private TextField phase = new TextField("Phase");
    private TextField kapitalbedarf = new TextField("Kapitalbedarf");
    private TextArea beschreibung = new TextArea("Beschreibung");

    private Button create = new Button("Create");
    private Button cancel = new Button("Cancel");

    private Binder<GruendungsideeDTOImpl> binder =
            new Binder<>(GruendungsideeDTOImpl.class);

    public GruendungsideeView(GruendungsideeControl control, ChatControl chatControl, LoginControl loginControl, StudentRepository studentRepository) {
        super(chatControl, loginControl);
        this.studentRepository = studentRepository;

        addClassName("enter-idea-view");

        add(createTitle());
        add(createFormLayout());
        add(createButtonLayout());

        binder.bindInstanceFields(this);
        clearForm();

        cancel.addClickListener(event -> clearForm());

        create.addClickListener(event -> {
            GruendungsideeDTOImpl dto = binder.getBean();

            User currentUser = loginControl.getCurrentUser();

            control.createIdea(dto, currentUser);

            Notification.show("Gründungsidee gespeichert.");
            clearForm();
        });
    }

    private Component createTitle() {
        return new H3("Gründungsidee erstellen");
    }



    private Component createFormLayout() {
        FormLayout formLayout = new FormLayout();
        int charLimit = 255;
        beschreibung.setMaxLength(charLimit);
        beschreibung.setValueChangeMode(ValueChangeMode.EAGER);
        beschreibung.addValueChangeListener (e -> {
            e.getSource().setHelperText(e.getValue().length()+"/"+charLimit);
        });
        formLayout.add(titel, kategorie, phase, kapitalbedarf, beschreibung);
        return formLayout;
    }

    private Component createButtonLayout() {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.addClassName("button-layout");

        create.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        buttonLayout.add(create);
        buttonLayout.add(cancel);

        return buttonLayout;
    }
    private void clearForm() {
        binder.setBean(new GruendungsideeDTOImpl());
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        User currentUser = loginControl.getCurrentUser();

        boolean isStudent = currentUser != null
                && studentRepository.findByUserId(currentUser.getId()).isPresent();

        if (!isStudent) {
            Notification.show("Nur Studenten können Gründungsideen erstellen.");
            event.rerouteTo(ShowIdeasView.class);
        }
    }

}
