package com.nutrilog.nutrilog_backend.supplement.dto;

import java.time.LocalTime;
import java.util.List;

import com.nutrilog.nutrilog_backend.supplement.Status;
import com.nutrilog.nutrilog_backend.supplement.entity.Supplement;
import com.nutrilog.nutrilog_backend.supplement.entity.SupplementSchedule;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SuppelementScheduleListResponse {
    
    private Long id;
    private Long supplementId;
    private String supplementName;
    private List<LocalTime> scheduledTime;
    private Status status;

//    public SuppelementScheduleListResponse(Supplement supplement, SupplementSchedule supplementSchedule) {
//        this.id = supplementSchedule.getId();
//        this.supplementId = supplement.getId();
//        this.supplementName = supplement.getName();
//        this.scheduledTime = supplementSchedule.getScheduledTime();
//
//    }
}
