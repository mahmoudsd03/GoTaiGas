package com.gotaigas.util;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;

import com.gotaigas.dtos.UserDTO;
import com.gotaigas.dtos.impl.UserDTOImpl;
import com.gotaigas.entities.User;
import com.gotaigas.repository.UserRepository;

import com.vaadin.flow.component.UI;

public class Utils {

    /**
     * Nützliche Methdode zur Erweiterung eines bestehendes Arrays
     * Oma hätte gesagt, so eine Methode 'fällt nicht durch' ;-)
     *
     * https://stackoverflow.com/questions/2843366/how-to-add-new-elements-to-an-array
     */


    public static <T> T[] append(T[] arr, T element) {
        final int N = arr.length;
        arr = Arrays.copyOf(arr, N + 1);
        arr[N] = element;
        return arr;

    }

    public static boolean isBigDecimal(String str) {
        if (str == null) {
            return false;
        }
        try {
            new BigDecimal(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public static void setCurrentUser (User user) {
        UserDTOImpl dto = new UserDTOImpl();
        dto.setFirstname(user.getFirstName());
        dto.setLastname(user.getLastName());
        dto.setId(user.getId());
        
        UI.getCurrent().getSession().setAttribute( Globals.CURRENT_USER, dto );
    }

    public static User getCurrentUser (UserRepository repo) {
        UserDTO dto = (UserDTO) UI.getCurrent().getSession().getAttribute(Globals.CURRENT_USER);
        if (dto == null)
            return null;
        Optional<User> user = repo.findUserById(dto.getId());
        return user.isPresent() ? user.get() : null;
    }
}
