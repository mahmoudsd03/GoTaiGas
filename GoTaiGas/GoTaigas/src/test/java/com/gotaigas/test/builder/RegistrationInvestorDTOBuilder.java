package com.gotaigas.test.builder;

import com.gotaigas.dtos.impl.RegistrationInvestorDTOImpl;

import java.time.LocalDate;

public class RegistrationInvestorDTOBuilder {

    private final RegistrationInvestorDTOImpl dto =
            new RegistrationInvestorDTOImpl();

    public RegistrationInvestorDTOBuilder() {
        dto.setFirstname("Max");
        dto.setLastname("Investor");
        dto.setEmail("investor@test.de");
        dto.setPassword("Start1234");
        dto.setBirthday(LocalDate.of(1995, 5, 5));

        dto.setFirma("BMW Ventures");
        dto.setInvestmentFokus("KI");
        dto.setBudget("250000");
    }

    public RegistrationInvestorDTOBuilder withFirstname(String firstname) {
        dto.setFirstname(firstname);
        return this;
    }

    public RegistrationInvestorDTOBuilder withLastname(String lastname) {
        dto.setLastname(lastname);
        return this;
    }

    public RegistrationInvestorDTOBuilder withEmail(String email) {
        dto.setEmail(email);
        return this;
    }

    public RegistrationInvestorDTOBuilder withPassword(String password) {
        dto.setPassword(password);
        return this;
    }

    public RegistrationInvestorDTOBuilder withBirthday(LocalDate birthday) {
        dto.setBirthday(birthday);
        return this;
    }

    public RegistrationInvestorDTOBuilder withFirma(String firma) {
        dto.setFirma(firma);
        return this;
    }

    public RegistrationInvestorDTOBuilder withInvestmentFokus(
            String investmentFokus
    ) {
        dto.setInvestmentFokus(investmentFokus);
        return this;
    }

    public RegistrationInvestorDTOBuilder withBudget(String budget) {
        dto.setBudget(budget);
        return this;
    }

    public RegistrationInvestorDTOImpl build() {
        return dto;
    }
}