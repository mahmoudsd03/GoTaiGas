package com.gotaigas.control.factories;

import com.gotaigas.dtos.RegistrationStudentDTO;
import com.gotaigas.entities.Student;
import com.gotaigas.entities.User;

public class StudentFactory {
    public static Student createStudent(RegistrationStudentDTO dto, User user) {
        Student student = new Student();

        student.setSemester(Integer.parseInt(dto.getSemester()));
        student.setStudiengang(dto.getStudiengang());
        student.setHochschule(dto.getHochschule());
        student.setUser(user);

        return student;
    }
}
