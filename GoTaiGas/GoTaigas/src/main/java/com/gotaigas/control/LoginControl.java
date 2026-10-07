package com.gotaigas.control;

import com.gotaigas.control.exception.DatabaseUserException;
import com.gotaigas.entities.User;
import com.gotaigas.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class LoginControl {

    private final UserRepository repository;

    private User user = null;

    public LoginControl(UserRepository repository) {
        this.repository = repository;
    }

    public boolean authentificate(String email, String password) throws DatabaseUserException {

        User tmpUser = this.getUserWithJPA(email, password);



        if (tmpUser == null) {
            return false;
        }

        this.user = tmpUser;
        return true;
    }

    public User getCurrentUser() {
        return this.user;
    }

    private User getUserWithJPA(String email, String password) throws DatabaseUserException {
        try {
            return repository.findUserByEmailAndPassword(email, password).orElse(null);
        } catch (org.springframework.dao.DataAccessException e) {
            throw new DatabaseUserException(
                    "A failure occurred while trying to connect to the database with JPA"
            );
        }
    }
}
