package com.gotaigas.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "investor", schema = "gotaigas")
public class Investor {

    private int id;
    private User user;
    private String firma;
    private String investmentFokus;
    private BigDecimal budget;

    @Id
    @Column(name = "investor_id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    @OneToOne
    @MapsId
    @JoinColumn(name = "investor_id")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    @Column(name = "firma")
    public String getFirma() {
        return firma;
    }

    public void setFirma(String firma) {
        this.firma = firma;
    }


    @Column(name = "investment_fokus")
    public String getInvestmentFokus() {
        return investmentFokus;
    }

    public void setInvestmentFokus(String investmentFokus) {
        this.investmentFokus = investmentFokus;
    }


    @Column(name = "budget")
    public BigDecimal getBudget() {
        return budget;
    }

    public void setBudget(BigDecimal budget) {
        this.budget = budget;
    }
}