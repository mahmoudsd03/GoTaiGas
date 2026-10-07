package com.gotaigas.test.repository;

import com.gotaigas.entities.Student;
import com.gotaigas.entities.User;
import com.gotaigas.repository.StudentRepository;
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
class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void findByUserIdReturnsStudent() {

        User user = new User();
        user.setFirstName("Amina");
        user.setLastName("Tester");
        user.setEmail("studentrepo@test.de");
        user.setPassword("Start1234");
        user.setDateOfBirth(LocalDate.of(2000,1,1));

        User savedUser = userRepository.save(user);

        Student student = new Student();
        student.setUser(savedUser);
        student.setHochschule("Musteruniversität");
        student.setStudiengang("Wirtschaftsinformatik");
        student.setSemester(4);

        studentRepository.save(student);

        Optional<Student> result =
                studentRepository.findByUserId(savedUser.getId());

        assertTrue(result.isPresent());
        assertEquals(savedUser.getId(), result.get().getUser().getId());
    }

    @Test
    void findByUserIdReturnsEmptyWhenStudentDoesNotExist() {

        Optional<Student> result =
                studentRepository.findByUserId(-9999);

        assertTrue(result.isEmpty());
    }
}