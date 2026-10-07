package com.gotaigas.control;

import com.gotaigas.repository.UserRepository;

import org.springframework.stereotype.Component;

@Component
public class RegistrationUserControl extends RegistrationControl {
    public RegistrationUserControl (UserRepository repository) {
        super(repository);
    }
}