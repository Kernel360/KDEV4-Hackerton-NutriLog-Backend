package com.nutrilog.nutrilog_backend.supplement.controller;

import java.util.List;

import com.nutrilog.nutrilog_backend.common.entities.User;
import com.nutrilog.nutrilog_backend.supplement.Status;
import com.nutrilog.nutrilog_backend.supplement.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import com.nutrilog.nutrilog_backend.supplement.service.SupplementService;

import lombok.RequiredArgsConstructor;



@RestController
@RequestMapping("/api/supplements")
@RequiredArgsConstructor
public class SupplementController {

    private final SupplementService supplementService;

    @PostMapping("/schedules")
    public ResponseEntity<Void> addSupplementSchedule(
            @RequestBody CreateSupplementScheduleRequest createSupplementScheduleRequest,
            @AuthenticationPrincipal User userDetails) {

        supplementService.addSupplementSchedule(createSupplementScheduleRequest, userDetails);
        return ResponseEntity.noContent().build();
    }


    @PatchMapping("/schedules/{supplementId}")
    public UpdateSupplementScheduleResponse updateSupplementSchedule(
            @PathVariable Long supplementId,
            @RequestBody UpdateSupplementScheduleRequest updateSupplementScheduleRequest) {

        return supplementService.updateSupplementSchedule(supplementId, updateSupplementScheduleRequest);
    }

    // 영양제 별 알림 여부 변경
    @PatchMapping("/notification/{supplementId}")
    public ResponseEntity<Boolean> updateSupplementNotification(
            @PathVariable Long supplementId,
            @RequestParam Boolean isNotification) {

        supplementService.updateSupplementNotification(supplementId, isNotification);

        return ResponseEntity.ok(isNotification);
    }

    // 영양제 복용 체크
    @PatchMapping("/history/{historyId}")
    public ResponseEntity<Void> updateSupplementHistory(@PathVariable Long historyId,
                                                        @RequestParam Status status) {

        supplementService.updateSupplementHistory(historyId, status);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/schedules/{supplementId}")
    public ResponseEntity<Void> deleteSupplementSchedule(
            @PathVariable Long supplementId) {

        supplementService.deleteSupplementSchedule(supplementId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{month}/{day}")
    public List<SuppelementScheduleListResponse> getSupplementList(
            @PathVariable int month,
            @PathVariable int day) {

        return supplementService.getSupplementList(month, day);
    }
    
    
}
