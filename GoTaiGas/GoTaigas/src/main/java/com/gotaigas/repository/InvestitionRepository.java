package com.gotaigas.repository;

import com.gotaigas.entities.Investition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvestitionRepository extends JpaRepository<Investition, Integer> {
}