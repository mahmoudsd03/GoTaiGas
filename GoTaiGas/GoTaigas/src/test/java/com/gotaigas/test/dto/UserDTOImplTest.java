package com.gotaigas.test.dto;

import static org.junit.jupiter.api.Assertions.*;

import com.gotaigas.dtos.impl.UserDTOImpl;
import org.junit.jupiter.api.Test;

public class UserDTOImplTest {

    @Test
    void testEqualsSameObject() {
        UserDTOImpl user = new UserDTOImpl();

        assertEquals(user, user);
    }

    @Test
    void testEqualsNull() {
        UserDTOImpl user = new UserDTOImpl();

        assertNotEquals(null, user);
    }

    @Test
    void testEqualsDifferentClass() {
        UserDTOImpl user = new UserDTOImpl();

        assertNotEquals("kein User", user.getName());
    }

    @Test
    void testEqualsSameValues() {
        UserDTOImpl user1 = new UserDTOImpl();
        UserDTOImpl user2 = new UserDTOImpl();

        user1.setUserID(String.valueOf(1));
        user2.setUserID(String.valueOf(1));

        user1.setName("Max");
        user2.setName("Max");

        user1.setPassword("1234");
        user2.setPassword("1234");

        user1.setAddress("Bonn");
        user2.setAddress("Bonn");

        user1.setGebDatum("01.01.2000");
        user2.setGebDatum("01.01.2000");

        assertEquals(user1, user2);
    }

    @Test
    void testEqualsDifferentValues() {
        UserDTOImpl user1 = new UserDTOImpl();
        UserDTOImpl user2 = new UserDTOImpl();

        user1.setName("Max");
        user2.setName("Moritz");

        assertNotEquals(user1, user2);
    }

    @Test
    void testHashCodeSameValues() {
        UserDTOImpl user1 = new UserDTOImpl();
        UserDTOImpl user2 = new UserDTOImpl();

        user1.setUserID(String.valueOf(5));
        user2.setUserID(String.valueOf(5));

        user1.setName("Max");
        user2.setName("Max");

        assertEquals(user1.hashCode(), user2.hashCode());
    }
    @Test
    void toStringTest() {
        final String TEST_PASSWORD = "testPassword";

        UserDTOImpl user1 = new UserDTOImpl();
        user1.setName("Max");
        user1.setPassword(TEST_PASSWORD);
        user1.setAddress("Bonn");
        user1.setGebDatum("01.01.2000");

        assertEquals("UserDTO [name=Max, userID=null, password=testPassword, gebDatum=01.01.2000, [opt] address=Bonn]",
                user1.toString());
    }
}