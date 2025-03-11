package com.nutrilog.nutrilog_backend.supplement.dto;

import java.util.List;

import com.nutrilog.nutrilog_backend.supplement.DaysOfWeek;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateSupplementScheduleRequest {

    private List<DaysOfWeek> daysOfWeek;
    private List<String> scheduledTimes;
    private boolean notificationEnabled;
}
