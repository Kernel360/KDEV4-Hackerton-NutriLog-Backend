package com.nutrilog.nutrilog_backend.supplement.dto;

import java.util.List;

import com.nutrilog.nutrilog_backend.supplement.DaysOfWeek;

import lombok.Builder;

@Builder
public class SupplementScheduleResponse {
    // private Long id;
    private Long supplementId;
    private String supplementName;
    private List<DaysOfWeek> daysOfWeek;
    private List<String> scheduledTime;
}
