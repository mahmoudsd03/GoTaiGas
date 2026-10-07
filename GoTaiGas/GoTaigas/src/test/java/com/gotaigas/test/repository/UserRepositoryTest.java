package com.gotaigas.test.repository;

import com.gotaigas.dtos.UserDTO;
import com.gotaigas.entities.User;
import com.gotaigas.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void findUserByEmailAndPasswordReturnsUser() {
        User savedUser = createAndSaveUser(
                "userrepo-login@test.de",
                "Amina",
                "Tester",
                "Start1234"
        );

        Optional<User> result =
                userRepository.findUserByEmailAndPassword("userrepo-login@test.de", "Start1234");

        assertTrue(result.isPresent());
        assertEquals(savedUser.getId(), result.get().getId());
        assertEquals("Amina", result.get().getFirstName());
        assertEquals("Tester", result.get().getLastName());
    }

    @Test
    void findUserByEmailReturnsUser() {
        User savedUser = createAndSaveUser(
                "userrepo-email@test.de",
                "Amina",
                "Tester",
                "Start1234"
        );

        Optional<User> result =
                userRepository.findUserByEmail("userrepo-email@test.de");

        assertTrue(result.isPresent());
        assertEquals(savedUser.getId(), result.get().getId());
    }

    @Test
    void findUserByLastNameReturnsUser() {
        createAndSaveUser(
                "userrepo-lastname@test.de",
                "Amina",
                "Testerlastname",
                "Start1234"
        );

        Optional<User> result =
                userRepository.findUserByLastName("Testerlastname");

        assertTrue(result.isPresent());
        assertEquals("Testerlastname", result.get().getLastName());
    }

    @Test
    void findUserByEmailReturnsEmptyWhenUserDoesNotExist() {
        Optional<User> result =
                userRepository.findUserByEmail("unknown-user@test.de");

        assertTrue(result.isEmpty());
    }

    private User createAndSaveUser(
            String email,
            String firstName,
            String lastName,
            String password
    ) {
        User user = new User();
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPassword(password);
        user.setDateOfBirth(LocalDate.of(2000, 1, 1));

        return userRepository.save(user);
    }
}