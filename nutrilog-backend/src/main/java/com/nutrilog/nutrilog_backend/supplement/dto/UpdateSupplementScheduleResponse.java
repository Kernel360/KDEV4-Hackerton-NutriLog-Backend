package com.nutrilog.nutrilog_backend.supplement.dto;

import java.time.LocalTime;
import java.util.List;

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

    private Long supplementId;
    private List<DaysOfWeek> daysOfWeek;
    private List<String> scheduledTime;
}
