package com.gotaigas.test.control;

import com.gotaigas.control.RegistrationResult;
import com.gotaigas.control.RegistrationStudentControl;
import com.gotaigas.control.exception.DatabaseUserException;
import com.gotaigas.dtos.impl.RegistrationStudentDTOImpl;
import com.gotaigas.entities.Student;
import com.gotaigas.entities.User;
import com.gotaigas.repository.StudentRepository;
import com.gotaigas.repository.UserRepository;
import com.gotaigas.test.builder.RegistrationStudentDTOBuilder;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.dao.DataAccessResourceFailureException;

import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RegistrationStudentControlTest {

    private StudentRepository studentRepository;
    private UserRepository userRepository;
    private RegistrationStudentControl control;

    @BeforeEach
    void setUp() {
        studentRepository = mock(StudentRepository.class);
        userRepository = mock(UserRepository.class);

        control = new RegistrationStudentControl(
                studentRepository,
                userRepository
        );
    }

    @Test
    void registrationStudentSuccessfulWithValidData()
            throws DatabaseUserException {

        RegistrationStudentDTOImpl dto =
                new RegistrationStudentDTOBuilder().build();

        when(userRepository.findUserByEmail(dto.getEmail()))
                .thenReturn(Optional.empty());

        RegistrationResult result = control.register(dto);

        assertTrue(result.getResult());
        assertEquals(
                RegistrationResult.REGISTRATION_SUCCESSFULL,
                result.getReason()
        );

        verify(studentRepository).save(any(Student.class));
    }

    @Test
    void registrationStudentSavesStudentWithCorrectData()
            throws DatabaseUserException {

        RegistrationStudentDTOImpl dto =
                new RegistrationStudentDTOBuilder().build();

        when(userRepository.findUserByEmail(dto.getEmail()))
                .thenReturn(Optional.empty());

        control.register(dto);

        ArgumentCaptor<Student> captor =
                ArgumentCaptor.forClass(Student.class);

        verify(studentRepository).save(captor.capture());

        Student savedStudent = captor.getValue();

        assertEquals("Musteruniversität", savedStudent.getHochschule());
        assertEquals(
                "Wirtschaftsinformatik",
                savedStudent.getStudiengang()
        );
        assertEquals(4, savedStudent.getSemester());

        assertNotNull(savedStudent.getUser());
        assertEquals(
                "student@test.de",
                savedStudent.getUser().getEmail()
        );
        assertEquals(
                "Amina",
                savedStudent.getUser().getFirstName()
        );
        assertEquals(
                "Tester",
                savedStudent.getUser().getLastName()
        );
        assertEquals(
                LocalDate.of(2000, 1, 1),
                savedStudent.getUser().getDateOfBirth()
        );
    }

    @Test
    void registrationFailsWhenSemesterIsNotNumeric()
            throws DatabaseUserException {

        RegistrationStudentDTOImpl dto =
                new RegistrationStudentDTOBuilder()
                        .withSemester("abc")
                        .build();

        RegistrationResult result = control.register(dto);

        assertFalse(result.getResult());
        assertEquals(
                RegistrationResult.SHOULD_BE_A_NUMBER,
                result.getReason()
        );

        verifyNoInteractions(userRepository);
        verify(studentRepository, never()).save(any(Student.class));
    }

    @Test
    void registrationFailsWhenSemesterIsNull()
            throws DatabaseUserException {

        RegistrationStudentDTOImpl dto =
                new RegistrationStudentDTOBuilder()
                        .withSemester(null)
                        .build();

        RegistrationResult result = control.register(dto);

        assertFalse(result.getResult());
        assertEquals(
                RegistrationResult.SHOULD_BE_A_NUMBER,
                result.getReason()
        );

        verifyNoInteractions(userRepository);
        verify(studentRepository, never()).save(any(Student.class));
    }

    @Test
    void registrationFailsWhenPasswordIsMissing()
            throws DatabaseUserException {

        RegistrationStudentDTOImpl dto =
                new RegistrationStudentDTOBuilder()
                        .withPassword("")
                        .build();

        RegistrationResult result = control.register(dto);

        assertFalse(result.getResult());
        assertEquals(
                RegistrationResult.PASSWORD_MISSING,
                result.getReason()
        );

        verify(studentRepository, never()).save(any(Student.class));
    }

    @Test
    void registrationFailsWhenEmailIsMissing()
            throws DatabaseUserException {

        RegistrationStudentDTOImpl dto =
                new RegistrationStudentDTOBuilder()
                        .withEmail("")
                        .build();

        RegistrationResult result = control.register(dto);

        assertFalse(result.getResult());
        assertEquals(
                RegistrationResult.EMAIL_MISSING,
                result.getReason()
        );

        verify(studentRepository, never()).save(any(Student.class));
    }

    @Test
    void registrationFailsWhenFirstnameIsMissing()
            throws DatabaseUserException {

        RegistrationStudentDTOImpl dto =
                new RegistrationStudentDTOBuilder()
                        .withFirstname("")
                        .build();

        RegistrationResult result = control.register(dto);

        assertFalse(result.getResult());
        assertEquals(
                RegistrationResult.FIRSTNAME_MISSING,
                result.getReason()
        );

        verify(studentRepository, never()).save(any(Student.class));
    }

    @Test
    void registrationFailsWhenLastnameIsMissing()
            throws DatabaseUserException {

        RegistrationStudentDTOImpl dto =
                new RegistrationStudentDTOBuilder()
                        .withLastname("")
                        .build();

        RegistrationResult result = control.register(dto);

        assertFalse(result.getResult());
        assertEquals(
                RegistrationResult.LASTNAME_MISSING,
                result.getReason()
        );

        verify(studentRepository, never()).save(any(Student.class));
    }

    @Test
    void registrationFailsWhenEmailIsInvalid()
            throws DatabaseUserException {

        RegistrationStudentDTOImpl dto =
                new RegistrationStudentDTOBuilder()
                        .withEmail("student-test.de")
                        .build();

        RegistrationResult result = control.register(dto);

        assertFalse(result.getResult());
        assertEquals(
                RegistrationResult.EMAIL_INVALID,
                result.getReason()
        );

        verify(studentRepository, never()).save(any(Student.class));
    }

    @Test
    void registrationFailsWhenPasswordIsTooShort()
            throws DatabaseUserException {

        RegistrationStudentDTOImpl dto =
                new RegistrationStudentDTOBuilder()
                        .withPassword("1234567")
                        .build();

        RegistrationResult result = control.register(dto);

        assertFalse(result.getResult());
        assertEquals(
                RegistrationResult.PASSWORD_TOO_SHORT,
                result.getReason()
        );

        verify(studentRepository, never()).save(any(Student.class));
    }

    @Test
    void registrationFailsWhenUserAlreadyExists()
            throws DatabaseUserException {

        RegistrationStudentDTOImpl dto =
                new RegistrationStudentDTOBuilder().build();

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

        verify(studentRepository, never()).save(any(Student.class));
    }

    @Test
    void registrationThrowsDatabaseUserExceptionWhenRepositoryFails() {

        RegistrationStudentDTOImpl dto =
                new RegistrationStudentDTOBuilder().build();

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

        verify(studentRepository, never()).save(any(Student.class));
    }

    @Test
    void updateChangesUserAndStudentData()
            throws DatabaseUserException {

        int userId = 10;

        RegistrationStudentDTOImpl dto =
                new RegistrationStudentDTOBuilder()
                        .withFirstname("Youssef")
                        .withLastname("Enhari")
                        .withPassword("NewPass123")
                        .withBirthday(LocalDate.of(1999, 5, 20))
                        .withHochschule("Beispielhochschule")
                        .withStudiengang("Informatik")
                        .withSemester("6")
                        .build();

        User existingUser = new User();
        existingUser.setId(userId);
        existingUser.setEmail(dto.getEmail());
        existingUser.setFirstName("Alt");
        existingUser.setLastName("Name");
        existingUser.setPassword("OldPassword");
        existingUser.setDateOfBirth(LocalDate.of(2000, 1, 1));

        Student existingStudent = new Student();
        existingStudent.setUser(existingUser);
        existingStudent.setHochschule("Bisherige Hochschule");
        existingStudent.setStudiengang("Alter Studiengang");
        existingStudent.setSemester(2);

        /*
         * Der Benutzer muss bereits existieren, damit update()
         * in die Aktualisierungslogik geht.
         */
        when(userRepository.findUserByEmail(dto.getEmail()))
                .thenReturn(Optional.of(existingUser));

        when(userRepository.findUserById(userId))
                .thenReturn(Optional.of(existingUser));

        when(studentRepository.findByUserId(userId))
                .thenReturn(Optional.of(existingStudent));

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

        assertEquals(
                "Beispielhochschule",
                existingStudent.getHochschule()
        );
        assertEquals(
                "Informatik",
                existingStudent.getStudiengang()
        );
        assertEquals(6, existingStudent.getSemester());

        verify(userRepository).save(existingUser);
        verify(studentRepository).save(existingStudent);
    }

    @Test
    void updateDoesNotSaveWhenSemesterIsInvalid()
            throws DatabaseUserException {

        RegistrationStudentDTOImpl dto =
                new RegistrationStudentDTOBuilder()
                        .withSemester("falsch")
                        .build();

        RegistrationResult result = control.update(dto, 10);

        assertFalse(result.getResult());
        assertEquals(
                RegistrationResult.SHOULD_BE_A_NUMBER,
                result.getReason()
        );

        verify(userRepository, never()).save(any(User.class));
        verify(studentRepository, never()).save(any(Student.class));
    }

    @Test
    void updateThrowsExceptionWhenStudentDoesNotExist() {

        int userId = 10;

        RegistrationStudentDTOImpl dto =
                new RegistrationStudentDTOBuilder().build();

        User existingUser = new User();
        existingUser.setId(userId);
        existingUser.setEmail(dto.getEmail());

        when(userRepository.findUserByEmail(dto.getEmail()))
                .thenReturn(Optional.of(existingUser));

        when(userRepository.findUserById(userId))
                .thenReturn(Optional.of(existingUser));

        when(studentRepository.findByUserId(userId))
                .thenReturn(Optional.empty());

        assertThrows(
                NoSuchElementException.class,
                () -> control.update(dto, userId)
        );

        verify(studentRepository, never()).save(any(Student.class));
    }
}