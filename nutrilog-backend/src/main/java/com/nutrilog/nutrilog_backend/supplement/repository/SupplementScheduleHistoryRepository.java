package com.nutrilog.nutrilog_backend.supplement.repository;


import com.nutrilog.nutrilog_backend.supplement.entity.SupplementScheduleHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplementScheduleHistoryRepository extends JpaRepository<SupplementScheduleHistory, Long> {
}