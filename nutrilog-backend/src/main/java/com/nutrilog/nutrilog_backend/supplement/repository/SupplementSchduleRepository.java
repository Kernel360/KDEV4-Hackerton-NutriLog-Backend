package com.nutrilog.nutrilog_backend.supplement.repository;

import java.time.DayOfWeek;
import java.util.List;

import com.nutrilog.nutrilog_backend.supplement.dto.SupplementResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nutrilog.nutrilog_backend.supplement.entity.SupplementSchedule;

@Repository
public interface SupplementSchduleRepository extends JpaRepository<SupplementSchedule, Long> {
    
     public List<SupplementSchedule> findBySupplementId(Long supplementId);

     @Query("SELECT DISTINCT new com.nutrilog.nutrilog_backend.supplement.dto.SupplementResponse(s.id, s.name) " +
             "FROM SupplementSchedule ss " +
             "JOIN ss.supplement s " +
             "WHERE ss.user.id = :userId")
     List<SupplementResponse> findSupplementsByUserId(@Param("userId") Long userId);

}
