package com.nutrilog.nutrilog_backend.supplement.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nutrilog.nutrilog_backend.supplement.dto.CreateSupplementScheduleRequest;
import com.nutrilog.nutrilog_backend.supplement.dto.SuppelementScheduleListResponse;
import com.nutrilog.nutrilog_backend.supplement.dto.SupplementScheduleResponse;
import com.nutrilog.nutrilog_backend.supplement.dto.UpdateSupplementScheduleRequest;
import com.nutrilog.nutrilog_backend.supplement.service.SupplementService;

import lombok.RequiredArgsConstructor;



@Controller
@RequestMapping("/api/supplements/schedules")
@RequiredArgsConstructor
public class SupplementController {

    private final SupplementService supplementService;
    
    @PostMapping()
    public SupplementScheduleResponse addSupplementSchedule(
            @RequestBody CreateSupplementScheduleRequest createSupplementScheduleRequest) {
        
        return supplementService.addSupplementSchedule(createSupplementScheduleRequest);
    }

//    @PatchMapping("/{schduledId}")
//    public SupplementScheduleResponse updateSupplementSchedule(
//            @PathVariable Long schduledId,
//            @RequestBody UpdateSupplementScheduleRequest UpdateSupplementScheduleRequest) {
//
//        return supplementService.updateSupplementSchedule(UpdateSupplementScheduleRequest);
//    }
//
//    @DeleteMapping("/{schduledId}")
//    public ResponseEntity<Void> deleteSupplementSchedule(
//            @PathVariable Long schduledId){
//
//        supplementService.deleteSupplementSchedule(schduledId);
//        return ResponseEntity.ok().build();
//    }
//
//    @GetMapping("/{month}/{day}")
//    public List<SuppelementScheduleListResponse> getSupplementList(
//            @PathVariable int month,
//            @PathVariable int day) {
//
//        return supplementService.getSupplementList(month, day);
//    }
    
    
}
