package com.gotaigas.dtos;

import java.util.Collections;
import java.util.List;

public interface UserDTO {

    int getId();

    String getFirstName();

    String getLastName();

    default List<RolleDTO> getRoles() {
        return Collections.emptyList();
    }
}
