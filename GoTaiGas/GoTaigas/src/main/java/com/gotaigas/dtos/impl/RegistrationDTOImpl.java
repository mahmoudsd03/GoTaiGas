package com.gotaigas.dtos.impl;

import com.gotaigas.dtos.RegistrationDTO;
import java.time.LocalDate;

public class RegistrationDTOImpl implements RegistrationDTO {

    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private LocalDate birthday;

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) { this.lastname = lastname; }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

}