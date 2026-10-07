package com.gotaigas.control.factories;

import com.gotaigas.dtos.RegistrationDTO;
import com.gotaigas.dtos.impl.UserDTOImpl;
import com.gotaigas.dtos.UserDTO;
import com.gotaigas.entities.User;

public class UserFactory {

	public static User createUser (RegistrationDTO dto) {
		User user = new User();

		user.setDateOfBirth(dto.getBirthday());
		user.setEmail(dto.getEmail());
		user.setFirstName(dto.getFirstname());
		user.setLastName(dto.getLastname());
		user.setPassword(dto.getPassword());

		return user;
	}
	
	public static UserDTO createNewUserWithNameAndPassword( String name, String password ) {
		UserDTOImpl dto = new UserDTOImpl();
		dto.setName(name);
		dto.setPassword(password);
		return dto;
	}
	
	public static UserDTO createDefaultUserWithNoPassword() {
		UserDTOImpl dto = (UserDTOImpl) UserFactory.getDefaultUser();
		dto.setPassword("");
		return dto;
	}
	
	public static UserDTO createDefaultUserWithNoPasswordAndNoAddress() {
		UserDTOImpl dto = (UserDTOImpl) UserFactory.getDefaultUser();
		dto.setPassword("");
		dto.setAddress("");
		return dto;
	}

	/**
	 * Erzeugung eines Default-Users mit vorbelegten Attributen
	 * (Template Pattern [GOF])
	 * @return
	 */
	private static UserDTO getDefaultUser() {
		UserDTOImpl dto = new UserDTOImpl();
		dto.setFirstname("Stefan Meyer");
		dto.setPassword("abc99");
		dto.setAddress("Bonn");
		dto.setGebDatum("25.9.1999");
		dto.setId(999);
		return dto;
	}

}
