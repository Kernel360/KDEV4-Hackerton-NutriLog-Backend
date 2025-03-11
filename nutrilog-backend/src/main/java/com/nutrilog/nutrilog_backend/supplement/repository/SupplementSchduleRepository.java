package com.nutrilog.nutrilog_backend.supplement.repository;

import java.time.DayOfWeek;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nutrilog.nutrilog_backend.supplement.entity.SupplementSchedule;

@Repository
public interface SupplementSchduleRepository extends JpaRepository<SupplementSchedule, Long> {
    
//     public List<SupplementSchedule> findByDayOfWeek(DayOfWeek dayOfWeek);
}
