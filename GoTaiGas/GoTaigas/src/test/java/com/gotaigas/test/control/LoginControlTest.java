package com.gotaigas.test.control;

import com.gotaigas.control.LoginControl;
import com.gotaigas.control.exception.DatabaseUserException;
import com.gotaigas.dtos.impl.UserDTOImpl;
import com.gotaigas.entities.User;
import com.gotaigas.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataAccessResourceFailureException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LoginControlTest {

    @Test
    void currentUserIsNullBeforeLogin() {
        UserRepository repository = mock(UserRepository.class);
        LoginControl loginControl = new LoginControl(repository);

        assertNull(loginControl.getCurrentUser());
    }

    @Test
    void loginSuccessfulWhenCredentialsAreCorrect() throws DatabaseUserException {
        UserRepository repository = mock(UserRepository.class);
        LoginControl loginControl = new LoginControl(repository);

        User user = new User();
        user.setId(1);
        user.setFirstName("Amina");
        user.setLastName("Tester");

        when(repository.findUserByEmailAndPassword("amina@test.de", "Start1234"))
                .thenReturn(Optional.of(user));

        boolean result = loginControl.authentificate("amina@test.de", "Start1234");

        assertTrue(result);
    }

    @Test
    void currentUserIsSetAfterSuccessfulLogin() throws DatabaseUserException {
        UserRepository repository = mock(UserRepository.class);
        LoginControl loginControl = new LoginControl(repository);

        User user = new User();
        user.setId(1);
        user.setFirstName("Amina");
        user.setLastName("Tester");

        when(repository.findUserByEmailAndPassword("amina@test.de", "Start1234"))
                .thenReturn(Optional.of(user));

        loginControl.authentificate("amina@test.de", "Start1234");

        assertNotNull(loginControl.getCurrentUser());
        assertEquals("Amina", loginControl.getCurrentUser().getFirstName());
    }

    @Test
    void loginFailsWhenUserDoesNotExist() throws DatabaseUserException {
        UserRepository repository = mock(UserRepository.class);
        LoginControl loginControl = new LoginControl(repository);

        when(repository.findUserByEmailAndPassword("wrong@test.de", "wrong"))
                .thenReturn(Optional.empty());

        boolean result = loginControl.authentificate("wrong@test.de", "wrong");

        assertFalse(result);
        assertNull(loginControl.getCurrentUser());
    }

    @Test
    void loginThrowsDatabaseUserExceptionWhenRepositoryFails() {
        UserRepository repository = mock(UserRepository.class);
        LoginControl loginControl = new LoginControl(repository);

        when(repository.findUserByEmailAndPassword("amina@test.de", "Start1234"))
                .thenThrow(new DataAccessResourceFailureException("Database not reachable"));

        assertThrows(DatabaseUserException.class, () ->
                loginControl.authentificate("amina@test.de", "Start1234")
        );
    }
}