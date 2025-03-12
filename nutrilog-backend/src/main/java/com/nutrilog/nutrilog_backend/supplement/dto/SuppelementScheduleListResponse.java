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

    private Long historyId;
    private String supplementName;
    private String scheduleTime;
    private String takenAt;
    private Status status;
}
