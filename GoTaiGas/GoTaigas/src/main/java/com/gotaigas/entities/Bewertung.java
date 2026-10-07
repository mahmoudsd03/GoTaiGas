package com.gotaigas.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "bewertung", schema = "gotaigas")
public class Bewertung {

    private int id;
    private Student student;
    private Gruendungsidee idee;
    private int sterne;
    private String kommentar;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bewertung_id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }


    @ManyToOne
    @JoinColumn(name = "idee_id", nullable = false)
    public Gruendungsidee getIdee() {
        return idee;
    }

    public void setIdee(Gruendungsidee idee) {
        this.idee = idee;
    }


    @Column(name = "sterne", nullable = false)
    public int getSterne() {
        return sterne;
    }

    public void setSterne(int sterne) {
        this.sterne = sterne;
    }


    @Column(name = "kommentar")
    public String getKommentar() {
        return kommentar;
    }

    public void setKommentar(String kommentar) {
        this.kommentar = kommentar;
    }
}