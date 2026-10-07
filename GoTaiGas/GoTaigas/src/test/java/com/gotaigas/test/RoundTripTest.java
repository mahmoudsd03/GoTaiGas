package com.gotaigas.test;

import com.gotaigas.dtos.UserDTO;
import com.gotaigas.entities.User;
import com.gotaigas.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class RoundTripTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void contextLoads() {
    }

    @Test
    void testUserRepositoryExists() {
        assertNotNull(userRepository);
    }

    @Test
    void testFindUserEntityById() {
        Optional<User> user = userRepository.findById(1);

        if (user.isPresent()) {
            System.out.println("User gefunden: " + user.get().getFirstName() + " " + user.get().getLastName());
            assertNotNull(user.get().getEmail());
        }
    }

    @Test
    void testFindUserByLastNameAndPassword() {
        Optional<User> userDTO = userRepository.findUserByEmail("xy@gmail.com");

        assertTrue(userDTO.isPresent(), "Benutzer wurde nicht gefunden. Prüfe lastName, Passwort und Datenbank-Dump.");

        System.out.println("User gefunden: " + userDTO.get().getFirstName() + " " + userDTO.get().getLastName());
    }
}