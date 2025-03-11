package com.nutrilog.nutrilog_backend.supplement.dto;

import java.util.List;

import com.nutrilog.nutrilog_backend.supplement.DaysOfWeek;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CreateSupplementScheduleRequest {
    

    @NotBlank(message = "이름은 비어있을 수 없습니다")
    private String name;

    @NotNull(message = "요일은 null일 수 없습니다")
    private List<DaysOfWeek> daysOfWeek;

    @NotEmpty(message = "시간은 비어있을 수 없습니다")
    private List<String> scheduledTime;

    @NotNull(message = "알림 설정은 null일 수 없습니다")
    private Boolean isNotificationEnabled;
}
