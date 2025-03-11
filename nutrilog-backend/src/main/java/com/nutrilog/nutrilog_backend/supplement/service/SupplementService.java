package com.nutrilog.nutrilog_backend.supplement.service;

import java.util.List;

import com.nutrilog.nutrilog_backend.supplement.dto.CreateSupplementScheduleRequest;
import com.nutrilog.nutrilog_backend.supplement.dto.SuppelementScheduleListResponse;
import com.nutrilog.nutrilog_backend.supplement.dto.SupplementScheduleResponse;
import com.nutrilog.nutrilog_backend.supplement.dto.UpdateSupplementScheduleRequest;

public interface SupplementService {
    
    public SupplementScheduleResponse addSupplementSchedule(CreateSupplementScheduleRequest createSupplementScheduleRequest);

//    public SupplementScheduleResponse updateSupplementSchedule(UpdateSupplementScheduleRequest UpdateSupplementScheduleRequest);
//
//    public void deleteSupplementSchedule(Long id);
//
//    public List<SuppelementScheduleListResponse> getSupplementList(int month, int day);
}
