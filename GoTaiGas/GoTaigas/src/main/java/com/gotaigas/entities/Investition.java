package com.gotaigas.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "investition", schema = "gotaigas")
public class Investition {

    private int id;
    private Investor investor;
    private Gruendungsidee idee;
    private BigDecimal betrag;
    private String status;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "investition_id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    @ManyToOne
    @JoinColumn(name = "investor_id", nullable = false)
    public Investor getInvestor() {
        return investor;
    }

    public void setInvestor(Investor investor) {
        this.investor = investor;
    }


    @ManyToOne
    @JoinColumn(name = "idee_id", nullable = false)
    public Gruendungsidee getIdee() {
        return idee;
    }

    public void setIdee(Gruendungsidee idee) {
        this.idee = idee;
    }


    @Column(name = "betrag", nullable = false)
    public BigDecimal getBetrag() {
        return betrag;
    }

    public void setBetrag(BigDecimal betrag) {
        this.betrag = betrag;
    }


    @Column(name = "status")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}