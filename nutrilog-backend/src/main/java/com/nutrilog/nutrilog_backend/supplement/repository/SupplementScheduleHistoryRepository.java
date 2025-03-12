package com.nutrilog.nutrilog_backend.supplement.repository;

import com.nutrilog.nutrilog_backend.supplement.Status;
import com.nutrilog.nutrilog_backend.supplement.entity.SupplementScheduleHistory;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
    // @Query("SELECT h FROM SupplementScheduleHistory h WHERE DATE(h.scheduledTime) = :scheduledDate")
    @Query("SELECT h FROM SupplementScheduleHistory h WHERE DATE(h.scheduledTime) = :scheduledDate AND h.user.id = :userId")
    List<SupplementScheduleHistory> findByScheduledDate(@Param("scheduledDate") LocalDate scheduledDate, Long userId);

    //  public List<SupplementScheduleHistory> findByScheduledDate(LocalDate scheduledDate);

//    public List<SupplementScheduleHistory> findByScheduledTimeBetween(LocalDateTime startOfDay, LocalDateTime endOfDay);

    List<SupplementScheduleHistory> findByScheduledTimeBetweenAndStatus(
            LocalDateTime start, LocalDateTime end, Status status);
}