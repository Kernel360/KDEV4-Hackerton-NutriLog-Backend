package com.nutrilog.nutrilog_backend.supplement.service;

import java.util.List;

import com.nutrilog.nutrilog_backend.common.entities.User;
import com.nutrilog.nutrilog_backend.supplement.Status;
import com.nutrilog.nutrilog_backend.supplement.dto.*;
import com.nutrilog.nutrilog_backend.supplement.dto.CreateSupplementScheduleRequest;

public interface SupplementService {
    
    public void addSupplementSchedule(CreateSupplementScheduleRequest createSupplementScheduleRequest, User userDetails);

    public UpdateSupplementScheduleResponse updateSupplementSchedule(Long supplementId, UpdateSupplementScheduleRequest UpdateSupplementScheduleRequest);

    public void updateSupplementNotification(Long supplementId, Boolean isNotification);

    public void updateSupplementHistory(Long historyId, Status status);
    public void deleteSupplementSchedule(Long supplementId);

    public void createSupplementScheduleHistory();

    public List<SuppelementScheduleListResponse> getSupplementList(int month, int day);

    public  List<SupplementResponse> getMySupplementList(Long userId);
}