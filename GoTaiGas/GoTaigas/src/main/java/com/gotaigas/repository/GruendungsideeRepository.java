package com.gotaigas.repository;

import com.gotaigas.entities.Gruendungsidee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GruendungsideeRepository extends JpaRepository<Gruendungsidee, Integer> {

    List<Gruendungsidee> findByTitelContainingIgnoreCase(String titel);

    List<Gruendungsidee> findByKategorieContainingIgnoreCase(String kategorie);
    List<Gruendungsidee> findByStudentId(int studentId);
}