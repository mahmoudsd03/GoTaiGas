package com.gotaigas.test.control;

import com.gotaigas.control.RegistrationInvestorControl;
import com.gotaigas.control.RegistrationResult;
import com.gotaigas.control.exception.DatabaseUserException;
import com.gotaigas.dtos.impl.RegistrationInvestorDTOImpl;
import com.gotaigas.entities.Investor;
import com.gotaigas.entities.User;
import com.gotaigas.repository.InvestorRepository;
import com.gotaigas.repository.UserRepository;
import com.gotaigas.test.builder.RegistrationInvestorDTOBuilder;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.dao.DataAccessResourceFailureException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RegistrationInvestorControlTest {

    private InvestorRepository investorRepository;
    private UserRepository userRepository;
    private RegistrationInvestorControl control;

    @BeforeEach
    void setUp() {
        investorRepository = mock(InvestorRepository.class);
        userRepository = mock(UserRepository.class);

        control = new RegistrationInvestorControl(
                investorRepository,
                userRepository
        );
    }

    @Test
    void registrationInvestorSuccessfulWithValidData()
            throws DatabaseUserException {

        RegistrationInvestorDTOImpl dto =
                new RegistrationInvestorDTOBuilder().build();

        when(userRepository.findUserByEmail(dto.getEmail()))
                .thenReturn(Optional.empty());

        RegistrationResult result = control.register(dto);

        assertTrue(result.getResult());
        assertEquals(
                RegistrationResult.REGISTRATION_SUCCESSFULL,
                result.getReason()
        );

        verify(investorRepository).save(any(Investor.class));
    }

    @Test
    void registrationInvestorSavesCorrectData()
            throws DatabaseUserException {

        RegistrationInvestorDTOImpl dto =
                new RegistrationInvestorDTOBuilder().build();

        when(userRepository.findUserByEmail(dto.getEmail()))
                .thenReturn(Optional.empty());

        control.register(dto);

        ArgumentCaptor<Investor> captor =
                ArgumentCaptor.forClass(Investor.class);

        verify(investorRepository).save(captor.capture());

        Investor savedInvestor = captor.getValue();

        assertEquals("BMW Ventures", savedInvestor.getFirma());
        assertEquals("KI", savedInvestor.getInvestmentFokus());
        assertEquals(
                0,
                savedInvestor.getBudget()
                        .compareTo(new BigDecimal("250000"))
        );

        assertNotNull(savedInvestor.getUser());
        assertEquals(
                "investor@test.de",
                savedInvestor.getUser().getEmail()
        );
        assertEquals(
                "Max",
                savedInvestor.getUser().getFirstName()
        );
        assertEquals(
                "Investor",
                savedInvestor.getUser().getLastName()
        );
        assertEquals(
                LocalDate.of(1995, 5, 5),
                savedInvestor.getUser().getDateOfBirth()
        );
    }

    @Test
    void registrationFailsWhenBudgetIsNotNumeric()
            throws DatabaseUserException {

        RegistrationInvestorDTOImpl dto =
                new RegistrationInvestorDTOBuilder()
                        .withBudget("abc")
                        .build();

        RegistrationResult result = control.register(dto);

        assertFalse(result.getResult());
        assertEquals(
                RegistrationResult.SHOULD_BE_A_NUMBER,
                result.getReason()
        );

        verifyNoInteractions(userRepository);
        verify(investorRepository, never())
                .save(any(Investor.class));
    }

    @Test
    void registrationFailsWhenBudgetIsNull()
            throws DatabaseUserException {

        RegistrationInvestorDTOImpl dto =
                new RegistrationInvestorDTOBuilder()
                        .withBudget(null)
                        .build();

        RegistrationResult result = control.register(dto);

        assertFalse(result.getResult());
        assertEquals(
                RegistrationResult.SHOULD_BE_A_NUMBER,
                result.getReason()
        );

        verifyNoInteractions(userRepository);
        verify(investorRepository, never())
                .save(any(Investor.class));
    }

    @Test
    void registrationAcceptsDecimalBudget()
            throws DatabaseUserException {

        RegistrationInvestorDTOImpl dto =
                new RegistrationInvestorDTOBuilder()
                        .withBudget("250000.50")
                        .build();

        when(userRepository.findUserByEmail(dto.getEmail()))
                .thenReturn(Optional.empty());

        RegistrationResult result = control.register(dto);

        assertTrue(result.getResult());

        ArgumentCaptor<Investor> captor =
                ArgumentCaptor.forClass(Investor.class);

        verify(investorRepository).save(captor.capture());

        assertEquals(
                0,
                captor.getValue()
                        .getBudget()
                        .compareTo(new BigDecimal("250000.50"))
        );
    }

    @Test
    void registrationFailsWhenPasswordIsMissing()
            throws DatabaseUserException {

        RegistrationInvestorDTOImpl dto =
                new RegistrationInvestorDTOBuilder()
                        .withPassword("")
                        .build();

        RegistrationResult result = control.register(dto);

        assertFalse(result.getResult());
        assertEquals(
                RegistrationResult.PASSWORD_MISSING,
                result.getReason()
        );

        verify(investorRepository, never())
                .save(any(Investor.class));
    }

    @Test
    void registrationFailsWhenEmailIsMissing()
            throws DatabaseUserException {

        RegistrationInvestorDTOImpl dto =
                new RegistrationInvestorDTOBuilder()
                        .withEmail("")
                        .build();

        RegistrationResult result = control.register(dto);

        assertFalse(result.getResult());
        assertEquals(
                RegistrationResult.EMAIL_MISSING,
                result.getReason()
        );

        verify(investorRepository, never())
                .save(any(Investor.class));
    }

    @Test
    void registrationFailsWhenFirstnameIsMissing()
            throws DatabaseUserException {

        RegistrationInvestorDTOImpl dto =
                new RegistrationInvestorDTOBuilder()
                        .withFirstname("")
                        .build();

        RegistrationResult result = control.register(dto);

        assertFalse(result.getResult());
        assertEquals(
                RegistrationResult.FIRSTNAME_MISSING,
                result.getReason()
        );

        verify(investorRepository, never())
                .save(any(Investor.class));
    }

    @Test
    void registrationFailsWhenLastnameIsMissing()
            throws DatabaseUserException {

        RegistrationInvestorDTOImpl dto =
                new RegistrationInvestorDTOBuilder()
                        .withLastname("")
                        .build();

        RegistrationResult result = control.register(dto);

        assertFalse(result.getResult());
        assertEquals(
                RegistrationResult.LASTNAME_MISSING,
                result.getReason()
        );

        verify(investorRepository, never())
                .save(any(Investor.class));
    }

    @Test
    void registrationFailsWhenEmailIsInvalid()
            throws DatabaseUserException {

        RegistrationInvestorDTOImpl dto =
                new RegistrationInvestorDTOBuilder()
                        .withEmail("investor-test.de")
                        .build();

        RegistrationResult result = control.register(dto);

        assertFalse(result.getResult());
        assertEquals(
                RegistrationResult.EMAIL_INVALID,
                result.getReason()
        );

        verify(investorRepository, never())
                .save(any(Investor.class));
    }

    @Test
    void registrationFailsWhenPasswordIsTooShort()
            throws DatabaseUserException {

        RegistrationInvestorDTOImpl dto =
                new RegistrationInvestorDTOBuilder()
                        .withPassword("1234567")
                        .build();

        RegistrationResult result = control.register(dto);

        assertFalse(result.getResult());
        assertEquals(
                RegistrationResult.PASSWORD_TOO_SHORT,
                result.getReason()
        );

        verify(investorRepository, never())
                .save(any(Investor.class));
    }

    @Test
    void registrationFailsWhenUserAlreadyExists()
            throws DatabaseUserException {

        RegistrationInvestorDTOImpl dto =
                new RegistrationInvestorDTOBuilder().build();

        User existingUser = new User();
        existingUser.setEmail(dto.getEmail());

        when(userRepository.findUserByEmail(dto.getEmail()))
                .thenReturn(Optional.of(existingUser));

        RegistrationResult result = control.register(dto);

        assertFalse(result.getResult());
        assertEquals(
                RegistrationResult.USER_EXISTS_ALREADY,
                result.getReason()
        );

        verify(investorRepository, never())
                .save(any(Investor.class));
    }

    @Test
    void registrationThrowsDatabaseUserExceptionWhenRepositoryFails() {

        RegistrationInvestorDTOImpl dto =
                new RegistrationInvestorDTOBuilder().build();

        when(userRepository.findUserByEmail(dto.getEmail()))
                .thenThrow(
                        new DataAccessResourceFailureException(
                                "Database unavailable"
                        )
                );

        DatabaseUserException exception = assertThrows(
                DatabaseUserException.class,
                () -> control.register(dto)
        );

        assertEquals(
                "A failure occurred while trying to connect " +
                        "to the database with JPA",
                exception.getReason()
        );

        verify(investorRepository, never())
                .save(any(Investor.class));
    }

    @Test
    void updateChangesUserAndInvestorData()
            throws DatabaseUserException {

        int userId = 20;

        RegistrationInvestorDTOImpl dto =
                new RegistrationInvestorDTOBuilder()
                        .withFirstname("Youssef")
                        .withLastname("Enhari")
                        .withPassword("NewPass123")
                        .withBirthday(LocalDate.of(1999, 5, 20))
                        .withFirma("Neue Firma")
                        .withInvestmentFokus("Software")
                        .withBudget("500000.75")
                        .build();

        User existingUser = new User();
        existingUser.setId(userId);
        existingUser.setEmail(dto.getEmail());
        existingUser.setFirstName("Alt");
        existingUser.setLastName("Name");
        existingUser.setPassword("OldPassword");
        existingUser.setDateOfBirth(LocalDate.of(2000, 1, 1));

        Investor existingInvestor = new Investor();
        existingInvestor.setUser(existingUser);
        existingInvestor.setFirma("Alte Firma");
        existingInvestor.setInvestmentFokus("Alt");
        existingInvestor.setBudget(new BigDecimal("1000"));

        when(userRepository.findUserByEmail(dto.getEmail()))
                .thenReturn(Optional.of(existingUser));

        when(userRepository.findUserById(userId))
                .thenReturn(Optional.of(existingUser));

        when(investorRepository.findByUserId(userId))
                .thenReturn(Optional.of(existingInvestor));

        RegistrationResult result = control.update(dto, userId);

        assertTrue(result.getResult());
        assertEquals(
                RegistrationResult.REGISTRATION_SUCCESSFULL,
                result.getReason()
        );

        assertEquals("Youssef", existingUser.getFirstName());
        assertEquals("Enhari", existingUser.getLastName());
        assertEquals("NewPass123", existingUser.getPassword());
        assertEquals(
                LocalDate.of(1999, 5, 20),
                existingUser.getDateOfBirth()
        );

        assertEquals("Neue Firma", existingInvestor.getFirma());
        assertEquals(
                "Software",
                existingInvestor.getInvestmentFokus()
        );
        assertEquals(
                0,
                existingInvestor.getBudget()
                        .compareTo(new BigDecimal("500000.75"))
        );

        verify(userRepository).save(existingUser);
        verify(investorRepository).save(existingInvestor);
    }

    @Test
    void updateDoesNotSaveWhenBudgetIsInvalid()
            throws DatabaseUserException {

        RegistrationInvestorDTOImpl dto =
                new RegistrationInvestorDTOBuilder()
                        .withBudget("falsch")
                        .build();

        RegistrationResult result = control.update(dto, 20);

        assertFalse(result.getResult());
        assertEquals(
                RegistrationResult.SHOULD_BE_A_NUMBER,
                result.getReason()
        );

        verify(userRepository, never()).save(any(User.class));
        verify(investorRepository, never())
                .save(any(Investor.class));
    }

    @Test
    void updateThrowsExceptionWhenInvestorDoesNotExist() {

        int userId = 20;

        RegistrationInvestorDTOImpl dto =
                new RegistrationInvestorDTOBuilder().build();

        User existingUser = new User();
        existingUser.setId(userId);
        existingUser.setEmail(dto.getEmail());

        when(userRepository.findUserByEmail(dto.getEmail()))
                .thenReturn(Optional.of(existingUser));

        when(userRepository.findUserById(userId))
                .thenReturn(Optional.of(existingUser));

        when(investorRepository.findByUserId(userId))
                .thenReturn(Optional.empty());

        assertThrows(
                NoSuchElementException.class,
                () -> control.update(dto, userId)
        );

        verify(investorRepository, never())
                .save(any(Investor.class));
    }
}