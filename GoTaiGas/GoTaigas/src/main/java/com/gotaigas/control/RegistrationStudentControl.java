package com.gotaigas.control;

import com.gotaigas.control.exception.DatabaseUserException;
import com.gotaigas.repository.StudentRepository;
import com.gotaigas.repository.UserRepository;
import com.gotaigas.control.factories.StudentFactory;
import com.gotaigas.dtos.RegistrationStudentDTO;
import com.gotaigas.dtos.RegistrationDTO;
import com.gotaigas.entities.Student;
import com.gotaigas.entities.User;
import com.gotaigas.util.Utils;

import org.springframework.stereotype.Component;

@Component
public class RegistrationStudentControl extends RegistrationControl {

    private final StudentRepository studentRepo;

    public RegistrationStudentControl (StudentRepository studentRepo, UserRepository repository) {
        super(repository);
        this.studentRepo = studentRepo;
    }

    @Override
	public RegistrationResult register( RegistrationDTO dto ) throws DatabaseUserException {
        RegistrationResult result = checkConstraints(dto);

        if (result.getResult()) {
            User user = registerUser(dto);
            Student student = StudentFactory.createStudent((RegistrationStudentDTO) dto, user);
            studentRepo.save(student);
        }
        return result;
    }

    @Override
    public RegistrationResult update (RegistrationDTO dto, int id) throws DatabaseUserException {
        RegistrationResult result = checkConstraints(dto);

        if (result.getReason().equals(RegistrationResult.USER_EXISTS_ALREADY)) {
            result = super.update(dto, id);
            Student student = studentRepo.findByUserId(id).get();
            if (student == null) {
                result.setResult(false);
                result.setReason("User does not exist");
            }else{
                RegistrationStudentDTO stuDTO = (RegistrationStudentDTO) dto;
                student.setHochschule(stuDTO.getHochschule());
                student.setStudiengang(stuDTO.getStudiengang());
                student.setSemester(Integer.parseInt(stuDTO.getSemester()));

                studentRepo.save(student);
            }
        }

        return result;
    }

    @Override
    protected RegistrationResult checkConstraints(RegistrationDTO dto) throws DatabaseUserException {
        RegistrationResult result = new RegistrationResult();
        result.setResult(true);
        if(!Utils.isNumeric(((RegistrationStudentDTO) dto).getSemester())) {
            result.setReason(RegistrationResult.SHOULD_BE_A_NUMBER);
            result.setResult(false);
        }
        if(result.getResult()) {
            result = super.checkConstraints(dto);
        }
        return result;
    }

}