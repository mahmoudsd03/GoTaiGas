package com.gotaigas.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "student", schema = "gotaigas")
public class Student {

    private int id;
    private User user;
    private String hochschule;
    private String studiengang;
    private Integer semester;

    @Id
    @Column(name = "student_id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    @OneToOne
    @MapsId
    @JoinColumn(name = "student_id")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    @Column(name = "hochschule")
    public String getHochschule() {
        return hochschule;
    }

    public void setHochschule(String hochschule) {
        this.hochschule = hochschule;
    }


    @Column(name = "studiengang")
    public String getStudiengang() {
        return studiengang;
    }

    public void setStudiengang(String studiengang) {
        this.studiengang = studiengang;
    }


    @Column(name = "semester")
    public Integer getSemester() {
        return semester;
    }

    public void setSemester(Integer semester) {
        this.semester = semester;
    }
}