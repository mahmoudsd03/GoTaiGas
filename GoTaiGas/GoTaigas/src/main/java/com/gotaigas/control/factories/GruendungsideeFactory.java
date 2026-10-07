package com.gotaigas.control.factories;

import com.gotaigas.dtos.GruendungsideeDTO;
import com.gotaigas.entities.Gruendungsidee;

import java.math.BigDecimal;

public class GruendungsideeFactory {

    public static Gruendungsidee createGruendungsidee(GruendungsideeDTO dto) {
        Gruendungsidee idee = new Gruendungsidee();

        idee.setTitel(dto.getTitel());
        idee.setBeschreibung(dto.getBeschreibung());
        idee.setKategorie(dto.getKategorie());
        idee.setPhase(dto.getPhase());
        idee.setKapitalbedarf(new BigDecimal(dto.getKapitalbedarf()));

        return idee;
    }
}