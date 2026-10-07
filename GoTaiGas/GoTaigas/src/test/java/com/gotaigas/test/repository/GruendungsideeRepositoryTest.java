package com.gotaigas.test.repository;

import com.gotaigas.entities.Gruendungsidee;
import com.gotaigas.entities.Student;
import com.gotaigas.entities.User;
import com.gotaigas.repository.GruendungsideeRepository;
import com.gotaigas.repository.StudentRepository;
import com.gotaigas.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class GruendungsideeRepositoryTest {

    @Autowired
    private GruendungsideeRepository gruendungsideeRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void saveAndFindByIdRoundTrip() {
        Student student = createAndSaveStudent("repo-roundtrip@test.de");
        Gruendungsidee savedIdea = createAndSaveIdea("Round Trip Idee", "Technologie", student);

        Optional<Gruendungsidee> foundIdea =
                gruendungsideeRepository.findById(savedIdea.getId());

        assertTrue(foundIdea.isPresent());
        assertEquals("Round Trip Idee", foundIdea.get().getTitel());
        assertEquals("Technologie", foundIdea.get().getKategorie());
        assertEquals(student.getId(), foundIdea.get().getStudent().getId());
    }

    @Test
    void findByTitelContainingIgnoreCaseReturnsMatchingIdeas() {
        Student student = createAndSaveStudent("repo-title@test.de");
        createAndSaveIdea("Innovative LernApp", "Education", student);

        List<Gruendungsidee> result =
                gruendungsideeRepository.findByTitelContainingIgnoreCase("lernapp");

        assertFalse(result.isEmpty());
        assertTrue(result.stream()
                .anyMatch(idea -> idea.getTitel().equals("Innovative LernApp")));
    }

    @Test
    void findByKategorieContainingIgnoreCaseReturnsMatchingIdeas() {
        Student student = createAndSaveStudent("repo-category@test.de");
        createAndSaveIdea("Smart Campus", "Technologie", student);

        List<Gruendungsidee> result =
                gruendungsideeRepository.findByKategorieContainingIgnoreCase("technologie");

        assertFalse(result.isEmpty());
        assertTrue(result.stream()
                .anyMatch(idea -> idea.getKategorie().equals("Technologie")));
    }

    private Student createAndSaveStudent(String email) {
        User user = new User();
        user.setFirstName("Amina");
        user.setLastName("Tester");
        user.setEmail(email);
        user.setPassword("Start1234");
        user.setDateOfBirth(LocalDate.of(2000, 1, 1));

        User savedUser = userRepository.save(user);

        Student student = new Student();
        student.setUser(savedUser);
        student.setHochschule("Musteruniversität");
        student.setStudiengang("Wirtschaftsinformatik");
        student.setSemester(4);

        return studentRepository.save(student);
    }

    private Gruendungsidee createAndSaveIdea(String titel, String kategorie, Student student) {
        Gruendungsidee idea = new Gruendungsidee();
        idea.setTitel(titel);
        idea.setBeschreibung("Testbeschreibung");
        idea.setKategorie(kategorie);
        idea.setPhase("Planung");
        idea.setKapitalbedarf(new BigDecimal("10000"));
        idea.setStudent(student);

        return gruendungsideeRepository.save(idea);
    }
}