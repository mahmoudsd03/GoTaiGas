package com.gotaigas.views;

import com.gotaigas.dtos.RegistrationDTO;
import com.gotaigas.dtos.impl.RegistrationDTOImpl;
import com.gotaigas.control.RegistrationResult;
import com.gotaigas.control.RegistrationControl;
import com.gotaigas.control.exception.DatabaseUserException;

import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "CreateAccount")
@PageTitle("Create Account")
public class RegistrationUserView extends RegistrationView {

    @Autowired
    private RegistrationControl registrationControl;

    @Override
    protected void clearForm() {
        binder.setBean(new RegistrationDTOImpl());
    }

    @Override
    protected void setBinder() {
        binder = new Binder(RegistrationDTOImpl.class);
    }

    @Override
    protected RegistrationResult registerUser(RegistrationDTO dto) throws DatabaseUserException {
        return registrationControl.register(dto);
    }

}

