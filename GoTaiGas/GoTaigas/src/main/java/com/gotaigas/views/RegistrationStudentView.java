package com.gotaigas.views;

import com.gotaigas.control.RegistrationStudentControl;
import com.gotaigas.dtos.impl.RegistrationStudentDTOImpl;
import com.gotaigas.entities.Student;
import com.gotaigas.entities.User;
import com.gotaigas.dtos.RegistrationDTO;
import com.gotaigas.control.RegistrationControl;
import com.gotaigas.control.RegistrationResult;
import com.gotaigas.control.exception.DatabaseUserException;

import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import org.springframework.beans.factory.annotation.Autowired;


@Route(value = "CreateStudentAccount")
@PageTitle("Create Student Account")
public class RegistrationStudentView extends RegistrationView {

    @Autowired
    private RegistrationStudentControl registrationControl;

    private TextField studiengang;
    private TextField semester;
    private TextField hochschule;

    @Override
    protected void clearForm() {
        binder.setBean(new RegistrationStudentDTOImpl());
    }

    @Override
    protected void setBinder() {
        binder = new Binder(RegistrationStudentDTOImpl.class);
    }

    @Override
    protected RegistrationResult registerUser(RegistrationDTO dto) throws DatabaseUserException {
        return registrationControl.register(dto);
    }

    @Override
    protected Component createTitle() {
        title = new H3("Student Registration");
        return title;
    }

    @Override
    protected Component createFormLayout() {
        FormLayout layout = (FormLayout) super.createFormLayout();

        studiengang = new TextField("Studiengang");
        semester = new TextField("Semester");
        hochschule = new TextField("Hochschule");

        layout.add(studiengang,semester,hochschule);
        return layout;
    }

    protected void changeProfile (RegistrationControl control, User user, Student student) {
        super.changeProfile(control,user);
        studiengang.setValue(student.getStudiengang() == null ? "" : student.getStudiengang());
        hochschule.setValue(student.getHochschule() == null ? "" : student.getHochschule());
        semester.setValue(student.getSemester() == null ? "" : ""+student.getSemester());
    }

}

