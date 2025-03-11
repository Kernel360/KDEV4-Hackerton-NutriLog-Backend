package com.nutrilog.nutrilog_backend.supplement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nutrilog.nutrilog_backend.supplement.entity.Supplement;

@Repository
public interface SupplementRepository extends JpaRepository<Supplement, Long> {
    
}
