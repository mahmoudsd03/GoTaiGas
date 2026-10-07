package com.gotaigas.test.builder;

import com.gotaigas.dtos.impl.RegistrationStudentDTOImpl;

import java.time.LocalDate;

public class RegistrationStudentDTOBuilder {

    private final RegistrationStudentDTOImpl dto =
            new RegistrationStudentDTOImpl();

    public RegistrationStudentDTOBuilder() {
        dto.setFirstname("Amina");
        dto.setLastname("Tester");
        dto.setEmail("student@test.de");
        dto.setPassword("Start1234");
        dto.setBirthday(LocalDate.of(2000, 1, 1));
        dto.setHochschule("Musteruniversität");
        dto.setStudiengang("Wirtschaftsinformatik");
        dto.setSemester("4");
    }

    public RegistrationStudentDTOBuilder withFirstname(String firstname) {
        dto.setFirstname(firstname);
        return this;
    }

    public RegistrationStudentDTOBuilder withLastname(String lastname) {
        dto.setLastname(lastname);
        return this;
    }

    public RegistrationStudentDTOBuilder withEmail(String email) {
        dto.setEmail(email);
        return this;
    }

    public RegistrationStudentDTOBuilder withPassword(String password) {
        dto.setPassword(password);
        return this;
    }

    public RegistrationStudentDTOBuilder withBirthday(LocalDate birthday) {
        dto.setBirthday(birthday);
        return this;
    }

    public RegistrationStudentDTOBuilder withHochschule(String hochschule) {
        dto.setHochschule(hochschule);
        return this;
    }

    public RegistrationStudentDTOBuilder withStudiengang(String studiengang) {
        dto.setStudiengang(studiengang);
        return this;
    }

    public RegistrationStudentDTOBuilder withSemester(String semester) {
        dto.setSemester(semester);
        return this;
    }

    public RegistrationStudentDTOImpl build() {
        return dto;
    }
}