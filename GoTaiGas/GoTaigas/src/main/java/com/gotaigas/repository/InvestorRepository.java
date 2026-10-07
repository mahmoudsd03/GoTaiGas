package com.gotaigas.repository;

import java.util.Optional;

import com.gotaigas.entities.Investor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvestorRepository extends JpaRepository<Investor, Integer> {
    Optional<Investor> findByUserId(int userId);
}