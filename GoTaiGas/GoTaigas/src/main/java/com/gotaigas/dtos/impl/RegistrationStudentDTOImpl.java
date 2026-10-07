package com.gotaigas.dtos.impl;

import com.gotaigas.dtos.RegistrationStudentDTO;

public class RegistrationStudentDTOImpl extends RegistrationDTOImpl implements RegistrationStudentDTO {
    private String studiengang;
    private String semester;
    private String hochschule;

    public String getStudiengang() {
        return this.studiengang;
    }

    public void setStudiengang(String studiengang) {
        this.studiengang = studiengang;
    }
    
    public String getSemester() {
        return this.semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getHochschule() {
        return this.hochschule;
    }

    public void setHochschule(String hochschule) {
        this.hochschule = hochschule;
    }

}