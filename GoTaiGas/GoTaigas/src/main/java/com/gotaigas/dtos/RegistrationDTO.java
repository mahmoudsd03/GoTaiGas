package com.gotaigas.dtos;

import java.time.LocalDate;

public interface RegistrationDTO {

    public String getFirstname();

    public String getLastname();

    public String getEmail();

    public String getPassword();

    public LocalDate getBirthday();

}