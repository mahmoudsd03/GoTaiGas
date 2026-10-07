package com.gotaigas.control;

import java.util.List;

import com.gotaigas.control.factories.GruendungsideeFactory;
import com.gotaigas.dtos.GruendungsideeDTO;
import com.gotaigas.entities.Gruendungsidee;
import com.gotaigas.repository.GruendungsideeRepository;
import org.springframework.stereotype.Component;
import com.gotaigas.entities.Student;
import com.gotaigas.entities.User;
import com.gotaigas.repository.StudentRepository;

@Component
public class GruendungsideeControl {

    private final GruendungsideeRepository repository;
    private final StudentRepository studentRepository;

    public GruendungsideeControl(GruendungsideeRepository repository, StudentRepository studentRepository) {
        this.repository = repository;
        this.studentRepository = studentRepository;
    }

    public void createIdea(GruendungsideeDTO dto, User currentUser) {

        Gruendungsidee idee = GruendungsideeFactory.createGruendungsidee(dto);

        Student student = studentRepository.findByUserId(currentUser.getId())
                .orElseThrow(() -> new RuntimeException("Kein Student für aktuellen Benutzer gefunden"));

        idee.setStudent(student);

        repository.save(idee);
    }

    public void deleteIdea(Gruendungsidee idee, User currentUser) {
        if (currentUser == null
                || idee == null
                || idee.getStudent() == null
                || idee.getStudent().getUser() == null
                || idee.getStudent().getUser().getId() != currentUser.getId()) {
            throw new RuntimeException("Du darfst nur deine eigenen Gründungsideen löschen.");
        }

        repository.delete(idee);
    }

    public List<Gruendungsidee> readIdeasByStudent(User currentUser) {

        Student student = studentRepository.findByUserId(currentUser.getId())
                .orElseThrow(() ->
                        new RuntimeException("Student nicht gefunden"));

        return repository.findByStudentId(student.getId());
    }
    public List<Gruendungsidee> readAllIdeas() {
        return repository.findAll();
    }
}