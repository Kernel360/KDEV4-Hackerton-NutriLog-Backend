package com.nutrilog.nutrilog_backend.supplement.service.implement;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;

import com.nutrilog.nutrilog_backend.common.entities.User;
import org.springframework.stereotype.Service;

import com.nutrilog.nutrilog_backend.supplement.DaysOfWeek;
import com.nutrilog.nutrilog_backend.supplement.dto.CreateSupplementScheduleRequest;
import com.nutrilog.nutrilog_backend.supplement.dto.SuppelementScheduleListResponse;
import com.nutrilog.nutrilog_backend.supplement.dto.SupplementScheduleResponse;
import com.nutrilog.nutrilog_backend.supplement.dto.UpdateSupplementScheduleRequest;
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
    // private final User user = new User();

    @Override
    public SupplementScheduleResponse addSupplementSchedule(
            CreateSupplementScheduleRequest createSupplementScheduleRequest){

        Supplement supplement = Supplement.builder()
            .name(createSupplementScheduleRequest.getName())
            .isNotificationEnabled(createSupplementScheduleRequest.getIsNotificationEnabled())
            .createdAt(LocalDateTime.now())
            .build();

        supplementRepository.save(supplement);

//        List<LocalTime> scheduledTimes = createSupplementScheduleRequest.getScheduledTime().stream()
//                .map(time -> LocalTime.parse(time))
//                .collect(Collectors.toList());

        for (DaysOfWeek dayOfWeek : createSupplementScheduleRequest.getDaysOfWeek()) {
            for (String localTime : createSupplementScheduleRequest.getScheduledTime()){
                LocalTime time = LocalTime.parse(localTime);
                SupplementSchedule supplementSchedule = SupplementSchedule.builder()
                        // .user(user)
                        .supplement(supplement)
                        .daysOfWeek(dayOfWeek)
                        .scheduledTime(time)
                        .build();

                supplementScheduleRepository.save(supplementSchedule);
            }

        }

        return SupplementScheduleResponse.builder()
                // .id(supplementSchedule.getId())
                .supplementId(supplement.getId())
                .supplementName(supplement.getName())
                .daysOfWeek(createSupplementScheduleRequest.getDaysOfWeek())
                .scheduledTime(createSupplementScheduleRequest.getScheduledTime())
                .build();
    }

    // 영양제 이름 변경 불가능
//    @Override
//    @Transactional
//    public SupplementScheduleResponse updateSupplementSchedule(UpdateSupplementScheduleRequest updateSupplementScheduleRequest) {
//
//        Long scheduleId = updateSupplementScheduleRequest.getId();
//        SupplementSchedule supplementSchedule = (SupplementSchedule) supplementScheduleRepository.findById(scheduleId).get();
//
//        // 요일 변경
//        supplementSchedule.setDaysOfWeek(updateSupplementScheduleRequest.getDaysOfWeek());
//
//        // 시간 변경
//        List<LocalTime> scheduledTimes = updateSupplementScheduleRequest.getScheduledTime().stream()
//                .map(time -> LocalTime.parse(time))
//                .collect(Collectors.toList());
//        supplementSchedule.setScheduledTime(scheduledTimes);
//
//        // 알림 설정 변경
//        Supplement supplement = supplementSchedule.getSupplement();
//        supplement.setIsNotificationEnabled(updateSupplementScheduleRequest.isNotificationEnabled());
//
//        // 가독성 위해 작성(필요 없음)
//        supplementRepository.save(supplement);
//        supplementScheduleRepository.save(supplementSchedule);
//
//        return SupplementScheduleResponse.builder()
//                // .id(supplementSchedule.getId())
//                .supplementId(supplement.getId())
//                .supplementName(supplement.getName())
//                .daysOfWeek(updateSupplementScheduleRequest.getDaysOfWeek())
//                .scheduledTime(updateSupplementScheduleRequest.getScheduledTime())
//                .build();
//    }
//
//    @Override
//    @Transactional
//    public void deleteSupplementSchedule(Long id) {
//
//        SupplementSchedule supplementSchedule = supplementScheduleRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("404로 바꿔야됨" + id)); // @ExceptionHandler 적용 해야 함
//
//        // 영양제 삭제일 업데이트
//        Supplement supplement = supplementSchedule.getSupplement();
//        supplement.setDeletedAt(LocalDateTime.now());
//        supplementRepository.save(supplement);
//
//        // 영양제 스케쥴 삭제
//        supplementScheduleRepository.delete(supplementSchedule);
//    }
//
//    // day는 뭐지 ?
//    @Override
//    public List<SuppelementScheduleListResponse> getSupplementList(int month, int day) {
//
//        LocalDate targetDate = LocalDate.of(LocalDate.now().getYear(), month, day);
//        DayOfWeek targetDayOfWeek = targetDate.getDayOfWeek(); // 요청 날짜의 요일 정보 추출
//
//        List<SupplementSchedule> supplementSchedule = supplementScheduleRepository.findByDayOfWeek(targetDayOfWeek);
//
//        List<SuppelementScheduleListResponse> suppelementScheduleListResponses = new ArrayList<>();
//
//        // status 어떻게 넣음?
//        for (SupplementSchedule schedule : supplementSchedule){
//            Supplement supplement = schedule.getSupplement();
//            suppelementScheduleListResponses.add(new SuppelementScheduleListResponse(supplement, schedule));
//        }
//
//        return suppelementScheduleListResponses;
//    }
}
