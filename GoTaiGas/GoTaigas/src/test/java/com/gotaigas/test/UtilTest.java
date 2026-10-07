package com.gotaigas.test;

import static org.junit.jupiter.api.Assertions.*;

import com.gotaigas.util.Utils;
import org.junit.jupiter.api.Test;

class UtilsTest {

    @Test
    void testAppend() {
        String[] array = {"A", "B"};

        String[] result = Utils.append(array, "C");

        assertArrayEquals(
                new String[]{"A", "B", "C"},
                result
        );
    }

    @Test
    void testIsBigDecimalValid() {
        assertTrue(Utils.isBigDecimal("12.50"));
    }

    @Test
    void testIsBigDecimalInvalid() {
        assertFalse(Utils.isBigDecimal("Hallo"));
    }

    @Test
    void testIsBigDecimalNull() {
        assertFalse(Utils.isBigDecimal(null));
    }

    @Test
    void testIsNumericValid() {
        assertTrue(Utils.isNumeric("123"));
        assertTrue(Utils.isNumeric("12.5"));
    }

    @Test
    void testIsNumericInvalid() {
        assertFalse(Utils.isNumeric("abc"));
    }

    @Test
    void testIsNumericNull() {
        assertFalse(Utils.isNumeric(null));
    }
}