package com.nutrilog.nutrilog_backend.supplement.service.implement;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;

import com.nutrilog.nutrilog_backend.common.entities.User;
import com.nutrilog.nutrilog_backend.supplement.dto.*;
import org.springframework.stereotype.Service;

import com.nutrilog.nutrilog_backend.supplement.DaysOfWeek;
import com.nutrilog.nutrilog_backend.supplement.entity.Supplement;
import com.nutrilog.nutrilog_backend.supplement.entity.SupplementSchedule;
import com.nutrilog.nutrilog_backend.supplement.repository.SupplementRepository;
import com.nutrilog.nutrilog_backend.supplement.repository.SupplementSchduleRepository;
import com.nutrilog.nutrilog_backend.supplement.service.SupplementService;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SupplementServiceImpl implements SupplementService {

    private final SupplementRepository supplementRepository;
    private final SupplementSchduleRepository supplementScheduleRepository;

    @Override
    @Transactional
    public void addSupplementSchedule(
            CreateSupplementScheduleRequest createSupplementScheduleRequest,
            User userDetails){

        Supplement supplement = Supplement.builder()
            .name(createSupplementScheduleRequest.getName())
            .isNotificationEnabled(createSupplementScheduleRequest.getIsNotificationEnabled())
            .build();

        supplementRepository.save(supplement);

        for (DaysOfWeek dayOfWeek : createSupplementScheduleRequest.getDaysOfWeek()) {
            for (String localTime : createSupplementScheduleRequest.getScheduledTime()){
                LocalTime time = LocalTime.parse(localTime);
                SupplementSchedule supplementSchedule = SupplementSchedule.builder()
                        .user(userDetails)
                        .supplement(supplement)
                        .daysOfWeek(dayOfWeek)
                        .scheduledTime(time)
                        .build();

                supplementScheduleRepository.save(supplementSchedule);
            }
        }
    }


    @Override
    @Transactional
    public UpdateSupplementScheduleResponse updateSupplementSchedule(
            Long schduledId,
            UpdateSupplementScheduleRequest updateSupplementScheduleRequest) {

        SupplementSchedule supplementSchedule = (SupplementSchedule) supplementScheduleRepository.findById(schduledId).get();

//        // 요일 변경
//        supplementSchedule.setDaysOfWeek(updateSupplementScheduleRequest.getDaysOfWeek());
//
//        // 시간 변경
//        supplementSchedule.setScheduledTime(LocalTime.parse(updateSupplementScheduleRequest.getScheduledTime()));

        // 알림 설정 변경
        Supplement supplement = supplementSchedule.getSupplement();
        supplement.setNotificationEnabled(updateSupplementScheduleRequest.isNotificationEnabled());

        // 가독성 위해 작성(필요 없음)
        supplementRepository.save(supplement);
        supplementScheduleRepository.save(supplementSchedule);

        return UpdateSupplementScheduleResponse.builder()
                // .id(supplementSchedule.getId())
                .scheduleId(supplementSchedule.getId())
                .daysOfWeek(updateSupplementScheduleRequest.getDaysOfWeek())
                .scheduledTime(LocalTime.parse(updateSupplementScheduleRequest.getScheduledTime()))
                .build();
    }


    @Override
    @Transactional
    public void deleteSupplementSchedule(Long id) {

        SupplementSchedule supplementSchedule = supplementScheduleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("404로 바꿔야됨" + id)); // @ExceptionHandler 적용 해야 함

        // 영양제 삭제일 업데이트
        Supplement supplement = supplementSchedule.getSupplement();
        supplement.setDeletedAt(LocalDateTime.now());
        supplementRepository.save(supplement);

        // 영양제 스케쥴 삭제
        supplementScheduleRepository.delete(supplementSchedule);
    }
//
    // day는 뭐지 ?
//    @Override
//    public List<SuppelementScheduleListResponse> getSupplementList(int month, int day) {
//
//        // 요청 날짜의 요일 정보 추출
//        LocalDate targetDate = LocalDate.of(LocalDate.now().getYear(), month, day);
//        DayOfWeek targetDayOfWeek = targetDate.getDayOfWeek();
//
//        List<SupplementSchedule> supplementSchedule = supplementScheduleRepository.findByDayOfWeek(targetDayOfWeek);
//
//        List<SuppelementScheduleListResponse> suppelementScheduleListResponses = new ArrayList<>();
//
//        // status 어떻게 넣음?
//        for (SupplementSchedule schedule : supplementSchedule){
//            Supplement supplement = schedule.getSupplement();
//            suppelementScheduleListResponses.add(new SuppelementScheduleListResponse(supplement.getName(), schedule, ));
//        }
//
//        return suppelementScheduleListResponses;
//    }
}
