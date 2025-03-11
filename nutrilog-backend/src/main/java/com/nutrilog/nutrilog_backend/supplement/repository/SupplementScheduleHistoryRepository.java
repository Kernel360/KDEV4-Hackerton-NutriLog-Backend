package com.nutrilog.nutrilog_backend.supplement.repository;

import com.nutrilog.nutrilog_backend.supplement.entity.SupplementScheduleHistory;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplementScheduleHistoryRepository extends JpaRepository<SupplementScheduleHistory, Long> {

    // 기존 메소드 주석 처리 또는 삭제
    // @Query()
    // public List<SupplementScheduleHistory> findByScheduledTime(LocalDate scheduledDate);

    // LocalDate 기반으로 scheduledTime 을 조회하는 쿼리
    @Query("SELECT h FROM SupplementScheduleHistory h WHERE DATE(h.scheduledTime) = :scheduledDate")
    List<SupplementScheduleHistory> findByScheduledDate(@Param("scheduledDate") LocalDate scheduledDate);

    //  public List<SupplementScheduleHistory> findByScheduledDate(LocalDate scheduledDate);

//    public List<SupplementScheduleHistory> findByScheduledTimeBetween(LocalDateTime startOfDay, LocalDateTime endOfDay);
}