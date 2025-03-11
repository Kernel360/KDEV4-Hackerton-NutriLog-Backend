package com.nutrilog.nutrilog_backend.supplement.service;

import java.util.List;

import com.nutrilog.nutrilog_backend.common.entities.User;
import com.nutrilog.nutrilog_backend.supplement.dto.*;

public interface SupplementService {
    
    public void addSupplementSchedule(CreateSupplementScheduleRequest createSupplementScheduleRequest, User userDetails);

    public UpdateSupplementScheduleResponse updateSupplementSchedule(Long schduledId, UpdateSupplementScheduleRequest UpdateSupplementScheduleRequest);
//
    public void deleteSupplementSchedule(Long id);
//
//    public List<SuppelementScheduleListResponse> getSupplementList(int month, int day);
}
