package com.gotaigas.test.control;

import com.gotaigas.control.GruendungsideeControl;
import com.gotaigas.dtos.impl.GruendungsideeDTOImpl;
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

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class GruendungsideeControlTest {

    @Autowired
    private GruendungsideeControl control;

    @Autowired
    private GruendungsideeRepository gruendungsideeRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void readAllIdeasReturnsAllSavedIdeas() {
        Student student = createAndSaveStudent("manage1@test.de");

        createAndSaveIdea("Idee Eins", student);
        createAndSaveIdea("Idee Zwei", student);

        List<Gruendungsidee> ideas = control.readAllIdeas();

        assertTrue(ideas.size() >= 2);
        assertTrue(ideas.stream().anyMatch(idea -> idea.getTitel().equals("Idee Eins")));
        assertTrue(ideas.stream().anyMatch(idea -> idea.getTitel().equals("Idee Zwei")));
    }

    @Test
    void readAllIdeasReturnsEmptyListWhenNoIdeasExist() {
        gruendungsideeRepository.deleteAll();

        List<Gruendungsidee> ideas = control.readAllIdeas();

        assertTrue(ideas.isEmpty());
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

    private void createAndSaveIdea(String title, Student student) {
        Gruendungsidee idea = new Gruendungsidee();
        idea.setTitel(title);
        idea.setBeschreibung("Testbeschreibung");
        idea.setKategorie("Technologie");
        idea.setPhase("Planung");
        idea.setKapitalbedarf(new BigDecimal("10000"));
        idea.setStudent(student);

        gruendungsideeRepository.save(idea);
    }

    @Test
    void createIdeaSavesIdeaSuccessfully() {
        User user = createAndSaveUser("roundtrip1@test.de");
        createAndSaveStudent(user);

        User currentUser = createUserDTO(user);

        GruendungsideeDTOImpl dto = createIdeaDTO();

        control.createIdea(dto, currentUser);

        List<Gruendungsidee> ideas =
                gruendungsideeRepository.findByTitelContainingIgnoreCase("Test Idee");

        assertFalse(ideas.isEmpty());
        assertEquals("Test Idee", ideas.get(0).getTitel());
        assertEquals("Beschreibung der Testidee", ideas.get(0).getBeschreibung());
        assertEquals("Technologie", ideas.get(0).getKategorie());
        assertEquals("Planung", ideas.get(0).getPhase());
    }

    @Test
    void createIdeaLinksIdeaToCorrectStudent() {
        User user = createAndSaveUser("roundtrip2@test.de");
        Student student = createAndSaveStudent(user);

        User currentUser = createUserDTO(user);

        GruendungsideeDTOImpl dto = createIdeaDTO();

        control.createIdea(dto, currentUser);

        List<Gruendungsidee> ideas =
                gruendungsideeRepository.findByTitelContainingIgnoreCase("Test Idee");

        assertFalse(ideas.isEmpty());
        assertNotNull(ideas.get(0).getStudent());
        assertEquals(student.getId(), ideas.get(0).getStudent().getId());
    }

    @Test
    void createIdeaThrowsExceptionWhenStudentDoesNotExist() {
        User user = createAndSaveUser("nostudent@test.de");

        User currentUser = createUserDTO(user);

        GruendungsideeDTOImpl dto = createIdeaDTO();

        assertThrows(RuntimeException.class, () ->
                control.createIdea(dto, currentUser)
        );
    }

    private User createAndSaveUser(String email) {
        User user = new User();
        user.setFirstName("Amina");
        user.setLastName("Tester");
        user.setEmail(email);
        user.setPassword("Start1234");
        user.setDateOfBirth(LocalDate.of(2000, 1, 1));

        return userRepository.save(user);
    }

    private Student createAndSaveStudent(User user) {
        Student student = new Student();
        student.setUser(user);
        student.setHochschule("Musteruniversität");
        student.setStudiengang("Wirtschaftsinformatik");
        student.setSemester(4);

        return studentRepository.save(student);
    }

    private User createUserDTO(User user) {
        User dto = new User();
        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        return dto;
    }

    private GruendungsideeDTOImpl createIdeaDTO() {
        GruendungsideeDTOImpl dto = new GruendungsideeDTOImpl();
        dto.setTitel("Test Idee");
        dto.setBeschreibung("Beschreibung der Testidee");
        dto.setKategorie("Technologie");
        dto.setPhase("Planung");
        dto.setKapitalbedarf("10000");
        return dto;
    }
}