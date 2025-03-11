package com.nutrilog.nutrilog_backend.supplement.dto;

import java.time.LocalTime;

import com.nutrilog.nutrilog_backend.supplement.DaysOfWeek;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UpdateSupplementScheduleResponse {

//    private Long supplementId;
    private Long scheduleId;
    private DaysOfWeek daysOfWeek;
    private LocalTime scheduledTime;
}
