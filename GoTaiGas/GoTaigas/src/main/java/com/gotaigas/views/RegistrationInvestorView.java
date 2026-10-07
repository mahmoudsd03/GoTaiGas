package com.gotaigas.views;

import com.gotaigas.dtos.RegistrationDTO;
import com.gotaigas.dtos.impl.RegistrationInvestorDTOImpl;
import com.gotaigas.entities.Investor;
import com.gotaigas.entities.User;
import com.gotaigas.control.RegistrationResult;
import com.gotaigas.control.RegistrationControl;
import com.gotaigas.control.RegistrationInvestorControl;
import com.gotaigas.control.exception.DatabaseUserException;

import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "CreateInvestorAccount")
@PageTitle("Create Investor Account")
public class RegistrationInvestorView extends RegistrationView { // 3. Form (Spezialisierung / Vererbung)

    @Autowired
    private RegistrationInvestorControl registrationControl;

    private TextField firma;
    private TextField investmentFokus;
    private TextField budget;

    @Override
    protected void clearForm() {
        binder.setBean(new RegistrationInvestorDTOImpl());
    }

    @Override
    protected void setBinder() {
        binder = new Binder(RegistrationInvestorDTOImpl.class);
    }

    @Override
    protected RegistrationResult registerUser(RegistrationDTO dto) throws DatabaseUserException {
        return registrationControl.register(dto);
    }

    @Override
    protected Component createTitle() {
        title = new H3("Investor Registration");
        return title;
    }

    @Override
    protected Component createFormLayout() {
        FormLayout layout = (FormLayout) super.createFormLayout();
        firma = new TextField("Firma");
        investmentFokus = new TextField("InvestmentFokus");
        budget = new TextField("Budget");

        layout.add(firma,investmentFokus,budget);

        return layout;
    }

    protected void changeProfile (RegistrationControl control, User user, Investor investor) {
        super.changeProfile(control, user);
        firma.setValue(investor.getFirma() == null ? "" : investor.getFirma());
        investmentFokus.setValue(investor.getInvestmentFokus() == null ? "" : investor.getInvestmentFokus());
        budget.setValue(investor.getBudget() == null ? "" : ""+investor.getBudget());
    }

}

