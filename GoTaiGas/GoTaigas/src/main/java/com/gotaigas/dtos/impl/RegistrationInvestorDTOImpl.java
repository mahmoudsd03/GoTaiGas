package com.gotaigas.dtos.impl;

import com.gotaigas.dtos.RegistrationInvestorDTO;

public class RegistrationInvestorDTOImpl extends RegistrationDTOImpl implements RegistrationInvestorDTO {

    private String firma;
    private String investmentFokus;
    private String budget;

    public String getFirma() {
        return this.firma;
    }

    public void setFirma(String firma) {
        this.firma = firma;
    }
    
    public String getInvestmentFokus() {
        return this.investmentFokus;
    }

    public void setInvestmentFokus(String investmentFokus) {
        this.investmentFokus = investmentFokus;
    }

    public String getBudget() {
        return this.budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

}