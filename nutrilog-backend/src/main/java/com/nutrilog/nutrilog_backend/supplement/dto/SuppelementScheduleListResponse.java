package com.nutrilog.nutrilog_backend.supplement.dto;

import com.nutrilog.nutrilog_backend.supplement.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class SuppelementScheduleListResponse {

//    private Long supplementId;
    private String supplementName;
    private LocalDateTime scheduleTime;
    private LocalDateTime takenAt;
    private Status status;
}
