package com.nutrilog.nutrilog_backend.supplement.controller;

import java.util.List;

import com.nutrilog.nutrilog_backend.common.entities.User;
import com.nutrilog.nutrilog_backend.supplement.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.nutrilog.nutrilog_backend.supplement.service.SupplementService;

import lombok.RequiredArgsConstructor;



@RestController
@RequestMapping("/api/supplements/schedules")
@RequiredArgsConstructor
public class SupplementController {

    private final SupplementService supplementService;
    
    @PostMapping()
    public ResponseEntity<Void> addSupplementSchedule(
            @RequestBody CreateSupplementScheduleRequest createSupplementScheduleRequest,
            @AuthenticationPrincipal User userDetails) {

        supplementService.addSupplementSchedule(createSupplementScheduleRequest, userDetails);
        return ResponseEntity.noContent().build();
    }


    @PatchMapping("/{supplementId}")
    public UpdateSupplementScheduleResponse updateSupplementSchedule(
            @PathVariable Long supplementId,
            @RequestBody UpdateSupplementScheduleRequest updateSupplementScheduleRequest) {

        return supplementService.updateSupplementSchedule(supplementId, updateSupplementScheduleRequest);
    }

    @DeleteMapping("/{schduledId}")
    public ResponseEntity<Void> deleteSupplementSchedule(
            @PathVariable Long schduledId){

        supplementService.deleteSupplementSchedule(schduledId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{month}/{day}")
    public List<SuppelementScheduleListResponse> getSupplementList(
            @PathVariable int month,
            @PathVariable int day) {

        return supplementService.getSupplementList(month, day);
    }
    
    
}
