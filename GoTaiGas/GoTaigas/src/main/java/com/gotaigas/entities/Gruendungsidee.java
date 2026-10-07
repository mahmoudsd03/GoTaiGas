package com.gotaigas.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "gruendungsidee", schema = "gotaigas")
public class Gruendungsidee {

    private int id;
    private String titel;
    private String beschreibung;
    private String kategorie;
    private String phase;
    private BigDecimal kapitalbedarf;
    private Student student;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idee_id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    @Basic
    @Column(name = "titel", nullable = false)
    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }


    @Basic
    @Column(name = "beschreibung", nullable = false)
    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }


    @Column(name = "kategorie")
    public String getKategorie() {
        return kategorie;
    }

    public void setKategorie(String kategorie) {
        this.kategorie = kategorie;
    }


    @Column(name = "phase")
    public String getPhase() {
        return phase;
    }

    public void setPhase(String phase) {
        this.phase = phase;
    }


    @Column(name = "kapitalbedarf")
    public BigDecimal getKapitalbedarf() {
        return kapitalbedarf;
    }

    public void setKapitalbedarf(BigDecimal kapitalbedarf) {
        this.kapitalbedarf = kapitalbedarf;
    }


    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}