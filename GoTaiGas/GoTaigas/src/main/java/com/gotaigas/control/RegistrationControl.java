package com.gotaigas.control;

import com.gotaigas.control.exception.DatabaseUserException;
import com.gotaigas.dtos.RegistrationDTO;
import com.gotaigas.repository.UserRepository;

import com.gotaigas.entities.User;
import com.gotaigas.control.factories.UserFactory;


public abstract class RegistrationControl {

    private final UserRepository repository;

    public RegistrationControl (UserRepository repository) {
        this.repository = repository;
    }

	public RegistrationResult register( RegistrationDTO dto ) throws DatabaseUserException {
        RegistrationResult result = checkConstraints(dto);
		
        if(result.getResult()) {
            User user = registerUser(dto);
            repository.save(user);
		}

		return result;
	}
    protected User registerUser (RegistrationDTO dto) {
        User user = UserFactory.createUser(dto);
        return user;
    }

    protected RegistrationResult checkConstraints(RegistrationDTO dto) throws DatabaseUserException {

        RegistrationResult result = new RegistrationResult();

		if ( dto.getPassword() == null || dto.getPassword().equals("")) {
			result.setReason(RegistrationResult.PASSWORD_MISSING);
			result.setResult(false);
		} 
		else if (dto.getEmail() == null || dto.getEmail().equals("")) {
            result.setReason(RegistrationResult.EMAIL_MISSING);
            result.setResult(false);
        }
        else if (dto.getFirstname() == null || dto.getFirstname().equals("")) {
            result.setReason(RegistrationResult.FIRSTNAME_MISSING);
            result.setResult(false);
        } else if  (dto.getLastname() == null || dto.getLastname().isEmpty()){
            result.setReason(RegistrationResult.LASTNAME_MISSING);
            result.setResult(false);
        } else if (!dto.getEmail().contains("@")) {
            result.setReason(RegistrationResult.EMAIL_INVALID);
            result.setResult(false);
        }
        else if (dto.getPassword().length() < 8) {
            result.setReason(RegistrationResult.PASSWORD_TOO_SHORT);
            result.setResult(false);
        }
        else if (this.checkIfUserExists(dto.getEmail())) {
            result.setReason(RegistrationResult.USER_EXISTS_ALREADY);
            result.setResult(false);
        }else{
			result.setResult(true);
            result.setReason(RegistrationResult.REGISTRATION_SUCCESSFULL);
        }
        return result;
    }

    public RegistrationResult update (RegistrationDTO dto, int id) throws DatabaseUserException {
        RegistrationResult result = checkConstraints(dto);
        if (result.getReason().equals(RegistrationResult.USER_EXISTS_ALREADY)) {
            result.setReason(RegistrationResult.REGISTRATION_SUCCESSFULL);
            result.setResult(true);
            User user = repository.findUserById(id).get();
            if (user == null) {
                result.setResult(false);
                result.setReason("User does not exist");
            }else{
                user.setFirstName(dto.getFirstname());
                user.setLastName(dto.getLastname());
                user.setDateOfBirth(dto.getBirthday());
                user.setPassword(dto.getPassword());
                repository.save(user);
            }
        }
        return result;
    }

    protected boolean checkIfUserExists(String mail) throws DatabaseUserException {
        try{
            return repository.findUserByEmail(mail).isPresent();
        } catch (org.springframework.dao.DataAccessException e) {
            throw new DatabaseUserException(
                    "A failure occurred while trying to connect to the database with JPA"
            );
        }
    }

    protected User getUser(String mail) throws DatabaseUserException {
        try{
            return repository.findUserByEmail(mail).get();
        } catch (org.springframework.dao.DataAccessException e) {
            throw new DatabaseUserException(
                    "A failure occurred while trying to connect to the database with JPA"
            );
        }
    }

}
