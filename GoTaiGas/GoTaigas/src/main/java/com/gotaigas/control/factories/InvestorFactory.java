package com.gotaigas.control.factories;

import com.gotaigas.dtos.RegistrationInvestorDTO;
import com.gotaigas.entities.Investor;
import com.gotaigas.entities.User;

import java.math.BigDecimal;

public class InvestorFactory {
    public static Investor createInvestor(RegistrationInvestorDTO dto, User user) {
        Investor investor = new Investor();

        investor.setFirma(dto.getFirma());
        investor.setInvestmentFokus(dto.getInvestmentFokus());
        investor.setBudget(new BigDecimal(dto.getBudget()));
        investor.setUser(user);

        return investor;
    }
}