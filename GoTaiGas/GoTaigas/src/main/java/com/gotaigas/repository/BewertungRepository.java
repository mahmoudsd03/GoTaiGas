package com.gotaigas.repository;

import com.gotaigas.entities.Bewertung;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BewertungRepository extends JpaRepository<Bewertung, Integer> {
}