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
import com.nutrilog.nutrilog_backend.supplement.Status;
import com.nutrilog.nutrilog_backend.supplement.entity.SupplementScheduleHistory;
import com.nutrilog.nutrilog_backend.supplement.repository.SupplementScheduleHistoryRepository;
import org.springframework.scheduling.annotation.Scheduled;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class SupplementServiceImpl implements SupplementService {

    private final SupplementRepository supplementRepository;
    private final SupplementSchduleRepository supplementScheduleRepository;
    private final SupplementScheduleHistoryRepository supplementScheduleHistoryRepository;

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


    // 스케쥴 시간, 요일 변경
    @Override
    @Transactional
    public UpdateSupplementScheduleResponse updateSupplementSchedule(
            Long schduledId,
            UpdateSupplementScheduleRequest updateSupplementScheduleRequest) {

        SupplementSchedule supplementSchedule = (SupplementSchedule) supplementScheduleRepository.findById(schduledId).get();

        // --------------------------------
        // 리스트로 변경
        // 요일 변경
//        supplementSchedule.setDaysOfWeek(updateSupplementScheduleRequest.getDaysOfWeek());
//
//        // 시간 변경
//        supplementSchedule.setScheduledTime(LocalTime.parse(updateSupplementScheduleRequest.getScheduledTime()));
        // -------------------------------- 
//        // 알림 설정 변경
//        Supplement supplement = supplementSchedule.getSupplement();
//        supplement.setNotificationEnabled(updateSupplementScheduleRequest.isNotificationEnabled());

        // 가독성 위해 작성(필요 없음)
//        supplementRepository.save(supplement);
        supplementScheduleRepository.save(supplementSchedule);

        return UpdateSupplementScheduleResponse.builder()
//                 .id(supplement.getId())
                .scheduleId(supplementSchedule.getId())
//                .daysOfWeek(updateSupplementScheduleRequest.getDaysOfWeek())
//                .scheduledTime(LocalTime.parse(updateSupplementScheduleRequest.getScheduledTime()))
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

    @Override
    @Scheduled(cron = "0 */5 * * * *") // 5분마다 스케줄러 실행
//    @Scheduled(fixedRate = 10000)
    @Transactional
    public void createSupplementScheduleHistory() {
        log.info("스케줄러 시작: SupplementScheduleHistory 생성");
        List<SupplementSchedule> supplementSchedules = supplementScheduleRepository.findAll();
        log.info("SupplementSchedule 개수: {}", supplementSchedules.size());

        for (SupplementSchedule schedule : supplementSchedules) {
            SupplementScheduleHistory history = SupplementScheduleHistory.builder()
                    .user(schedule.getUser())
                    .supplement(schedule.getSupplement())
                    // .scheduledTime(schedule.getScheduledTime()) // 복용 계획 시간
                    .scheduledTime(LocalDateTime.of(LocalDate.now(), schedule.getScheduledTime()))
                    // 복용 시간은 null로 설정
                    .status(Status.UNTAKEN) // 복용 아직X
                    .build();
//            log.info("SupplementScheduleHistory 생성: scheduleId={}, userId={}, scheduledTime={}", schedule.getId(), schedule.getUser().getId(), schedule.getScheduledTime());

            supplementScheduleHistoryRepository.save(history);
            log.info(String.valueOf(history.getId()));
//            log.info("SupplementScheduleHistory 저장 완료: historyId={}", history.getId());
        }
        log.info("스케줄러 종료: SupplementScheduleHistory 생성 완료");
    }

    @Override
    public List<SuppelementScheduleListResponse> getSupplementList(int month, int day) {

        // 월, 일로 날짜 가져오기
        // 날짜로 히스토리 리스트 가져오기

        // 


       // 요청 날짜의 요일 정보 추출
        LocalDate nowDate = LocalDate.of(LocalDate.now().getYear(), month, day);

        log.info("날짜 정보:" + nowDate.toString());

        // nowDate 날의 히스토리 정보 리스트 반환
        List<SupplementScheduleHistory> supplementScheduleHistory = supplementScheduleHistoryRepository.findByScheduledDate(nowDate);

        List<SuppelementScheduleListResponse> suppelementScheduleListResponses = new ArrayList<>();
        // 선택한 날짜의 히스토리 리스트 저장해서 반환
        for (SupplementScheduleHistory scheduleHistory : supplementScheduleHistory) {
            log.info("히스토리 리스트 :" + scheduleHistory.getScheduledTime().toString());

            Supplement supplement = scheduleHistory.getSupplement();

            suppelementScheduleListResponses.add(SuppelementScheduleListResponse.builder()
//                    .supplementId(supplement.getId())
                    .supplementName(supplement.getName())
                    .scheduleTime(scheduleHistory.getScheduledTime())
                    .takenAt(scheduleHistory.getTakenAt())
                    .status(scheduleHistory.getStatus())
                    .build());
        }
        return suppelementScheduleListResponses;
    }
}
