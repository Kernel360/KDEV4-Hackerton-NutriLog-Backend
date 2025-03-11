package com.nutrilog.nutrilog_backend.supplement.dto;

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

    private DaysOfWeek daysOfWeek;
    private String scheduledTime;
    private boolean notificationEnabled;
}
