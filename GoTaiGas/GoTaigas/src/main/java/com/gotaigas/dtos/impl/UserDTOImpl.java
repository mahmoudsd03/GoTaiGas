package com.gotaigas.dtos.impl;

import com.gotaigas.dtos.UserDTO;
import com.gotaigas.dtos.RolleDTO;
import java.util.List;
import java.util.Objects;

public class UserDTOImpl implements UserDTO {
	private String name;
	private String userID;
	private String password;
	private String gebDatum;
	private String address;


    private int id;
    private String firstname;
    private String lastname;
    private List<RolleDTO> roles;

    public void setId(int id) {
        this.id = id;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setRoles(List<RolleDTO> roles) {
        this.roles = roles;
    }


    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public String getFirstName() {
        return this.firstname;
    }

    @Override
    public String getLastName() {
        return this.lastname;
    }

    @Override
    public List<RolleDTO> getRoles() {
        return this.roles;
    }

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getGebDatum() {
		return gebDatum;
	}
	public void setGebDatum(String gebDatum) {
		this.gebDatum = gebDatum;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "UserDTO [name=" + name + ", userID=" + userID + ", password=" + password + ", gebDatum=" + gebDatum
				+ ", [opt] address=" + address + "]";
	}
	/*
	löschen wenn approved von Amina
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserDTOImpl other = (UserDTOImpl) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (gebDatum == null) {
			if (other.gebDatum != null)
				return false;
		} else if (!gebDatum.equals(other.gebDatum))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (userID == null) {
			if (other.userID != null)
				return false;
		} else if (!userID.equals(other.userID))
			return false;
		return true;
	}
	*/
	@Override
	//Hier wird auch intern in der Object.equals auf Null geprüft
public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (!(obj instanceof UserDTOImpl other))
		return false;

	return Objects.equals(address, other.address)
			&& Objects.equals(gebDatum, other.gebDatum)
			&& Objects.equals(name, other.name)
			&& Objects.equals(password, other.password)
			&& Objects.equals(userID, other.userID);
}

@Override
public int hashCode() {
	return Objects.hash(address, gebDatum, name, password, userID);
}
}