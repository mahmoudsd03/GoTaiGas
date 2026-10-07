package com.gotaigas.test.dto;
import static org.junit.jupiter.api.Assertions.*;

import com.gotaigas.dtos.impl.GruendungsideeDTOImpl;
import org.junit.jupiter.api.Test;

public class GruendungsideeDTOImplTest {

        @Test
        void testGetterAndSetter() {
            GruendungsideeDTOImpl dto = new GruendungsideeDTOImpl();

            dto.setTitel("Meine App");
            dto.setBeschreibung("Eine neue Idee");
            dto.setKategorie("Technologie");
            dto.setPhase("Planung");
            dto.setKapitalbedarf("10000");

            assertEquals("Meine App", dto.getTitel());
            assertEquals("Eine neue Idee", dto.getBeschreibung());
            assertEquals("Technologie", dto.getKategorie());
            assertEquals("Planung", dto.getPhase());
            assertEquals("10000", dto.getKapitalbedarf());
        }
    }