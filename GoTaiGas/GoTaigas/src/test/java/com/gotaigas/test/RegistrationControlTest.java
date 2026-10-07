package com.gotaigas.test;

import com.gotaigas.control.exception.DatabaseUserException;
import java.time.LocalDate;
import com.gotaigas.control.RegistrationUserControl;
import com.gotaigas.control.RegistrationResult;
import com.gotaigas.dtos.impl.RegistrationDTOImpl;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessResourceFailureException;
import com.gotaigas.repository.UserRepository;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

    @SpringBootTest
    public class RegistrationControlTest {

        @Autowired
        private RegistrationUserControl control;

        @Test
        @Transactional
        void registrationSuccessfulWithValidData() {
                RegistrationDTOImpl dto = new RegistrationDTOImpl();
            dto.setFirstname("amina");
            dto.setLastname("Tester");
            dto.setEmail("amina@test.de");
            dto.setPassword("Start1234");

            try{
                RegistrationResult result = control.register(dto);
                assertTrue(result.getResult());
                assertEquals(RegistrationResult.REGISTRATION_SUCCESSFULL, result.getReason());
            }catch (DatabaseUserException e) {
                assertTrue(false);
            }
        }

        @Test
        void registrationFailsWhenPasswordIsMissing() {
            RegistrationDTOImpl dto = new RegistrationDTOImpl();
            dto.setFirstname("amina");
            dto.setLastname("Tester");
            dto.setEmail("amina@test.de");
            dto.setPassword("");

            try{
                RegistrationResult result = control.register(dto);

                assertFalse(result.getResult());
                assertEquals(RegistrationResult.PASSWORD_MISSING, result.getReason());
            }catch (DatabaseUserException e) {
                assertTrue(false);
            }
        }

        @Test
        void registrationFailsWhenEmailIsMissing() {
            RegistrationDTOImpl dto = new RegistrationDTOImpl();
            dto.setFirstname("amina");
            dto.setLastname("Tester");
            dto.setEmail("");
            dto.setPassword("Start1234");

            try{
                RegistrationResult result = control.register(dto);
                assertFalse(result.getResult());
                assertEquals(RegistrationResult.EMAIL_MISSING, result.getReason());
            }catch (DatabaseUserException e) {
                assertTrue(false);
            }
        }

        @Test
        void registrationFailsWhenFirstnameIsMissing() {
            RegistrationDTOImpl dto = new RegistrationDTOImpl();
            dto.setFirstname("");
            dto.setLastname("Tester");
            dto.setEmail("amina@test.de");
            dto.setPassword("Start1234");

            try{
                RegistrationResult result = control.register(dto);
                assertFalse(result.getResult());
                assertEquals(RegistrationResult.FIRSTNAME_MISSING, result.getReason());
            }catch (DatabaseUserException e) {
                assertTrue(false);
            }
        }

        @Test
        void registrationFailsWhenEmailIsInvalid() {
            RegistrationDTOImpl dto = new RegistrationDTOImpl();
            dto.setFirstname("amina");
            dto.setLastname("Tester");
            dto.setEmail("aminatest.de");
            dto.setPassword("Start1234");

            try{
                RegistrationResult result = control.register(dto);
                assertFalse(result.getResult());
                assertEquals(RegistrationResult.EMAIL_INVALID, result.getReason());
            }catch (DatabaseUserException e) {
                assertTrue(false);
            }
        }

        @Test
        void registrationFailsWhenLastnameIsMissing() {
            RegistrationDTOImpl dto = new RegistrationDTOImpl();
            dto.setFirstname("amina");
            dto.setLastname("");
            dto.setEmail("aminatest.de");
            dto.setPassword("Start1234");

            try{
                RegistrationResult result = control.register(dto);
                assertFalse(result.getResult());
                assertEquals(RegistrationResult.LASTNAME_MISSING, result.getReason());
            }catch (DatabaseUserException e) {
                assertTrue(false);
            }
        }
        @Test
        void registrationFailsWhenPasswordIsTooShort() {

            RegistrationDTOImpl dto = new RegistrationDTOImpl();
            dto.setFirstname("amina");
            dto.setLastname("Tester");
            dto.setEmail("amina@test.de");
            dto.setPassword("12345");

            try{
                RegistrationResult result = control.register(dto);
                assertFalse(result.getResult());
                assertEquals(RegistrationResult.PASSWORD_TOO_SHORT, result.getReason());
            }catch (DatabaseUserException e) {
                assertTrue(false);
            }
        }

        @Test
        @Transactional
        void registrationFailsWhenUserAlreadyExists() {
            RegistrationDTOImpl dto = new RegistrationDTOImpl();
            dto.setFirstname("amina");
            dto.setLastname("Tester");
            dto.setEmail("alreadyexists@test.de");
            dto.setPassword("Start1234");
            dto.setBirthday(LocalDate.of(2000, 1, 1));

            try {
                RegistrationResult firstResult = control.register(dto);
                assertTrue(firstResult.getResult());

                RegistrationResult secondResult = control.register(dto);
                assertFalse(secondResult.getResult());
                assertEquals(RegistrationResult.USER_EXISTS_ALREADY, secondResult.getReason());

            } catch (DatabaseUserException e) {
                fail("DatabaseUserException should not be thrown");
            }
        }

        @Test
        void registrationThrowsDatabaseUserExceptionWhenRepositoryFails() {
            UserRepository repository = mock(UserRepository.class);
            RegistrationUserControl registrationControl = new RegistrationUserControl(repository);

            RegistrationDTOImpl dto = new RegistrationDTOImpl();
            dto.setFirstname("amina");
            dto.setLastname("Tester");
            dto.setEmail("dberror@test.de");
            dto.setPassword("Start1234");

            when(repository.findUserByEmail("dberror@test.de"))
                    .thenThrow(new DataAccessResourceFailureException("Database not reachable"));

            assertThrows(DatabaseUserException.class, () ->
                    registrationControl.register(dto)
            );
        }
    }

