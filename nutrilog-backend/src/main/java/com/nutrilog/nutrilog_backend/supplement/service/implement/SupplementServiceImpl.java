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
            Long supplementId,
            UpdateSupplementScheduleRequest updateSupplementScheduleRequest) {

        List<SupplementSchedule> supplementSchedule = supplementScheduleRepository.findBySupplementId(supplementId);


        // 만약 원래 스케줄보다 적어지면 삭제하고 다시 생성
        int requestSize = updateSupplementScheduleRequest.getDaysOfWeek().size() * updateSupplementScheduleRequest.getScheduledTimes().size();

        if (supplementSchedule.size() != requestSize) {
            // 전부 삭제하고
            supplementScheduleRepository.deleteAll(supplementSchedule);

            // 새로운 값 넣기
            Supplement supplement = supplementRepository.findById(supplementId).get();
            for (int dayIndex = 0; dayIndex < updateSupplementScheduleRequest.getDaysOfWeek().size(); dayIndex++) {
                for (int timeIndex = 0; timeIndex < updateSupplementScheduleRequest.getScheduledTimes().size(); timeIndex++) {
                    supplementScheduleRepository.save(SupplementSchedule.builder()
                        .supplement(supplement)
                        .daysOfWeek(updateSupplementScheduleRequest.getDaysOfWeek().get(dayIndex))
                        .scheduledTime(LocalTime.parse(updateSupplementScheduleRequest.getScheduledTimes().get(timeIndex)))
                        .build()
                    );
                }
            }
        } else {
            // 기존 스케줄 업데이트 (요청 사이즈만큼만 업데이트)
             int scheduleIndex = 0;
             for (int dayIndex = 0; dayIndex < updateSupplementScheduleRequest.getDaysOfWeek().size(); dayIndex++) {
                 for (int timeIndex = 0; timeIndex < updateSupplementScheduleRequest.getScheduledTimes().size(); timeIndex++) {
                     supplementSchedule.get(scheduleIndex).setDaysOfWeek(updateSupplementScheduleRequest.getDaysOfWeek().get(dayIndex));
                     supplementSchedule.get(scheduleIndex).setScheduledTime(LocalTime.parse(updateSupplementScheduleRequest.getScheduledTimes().get(timeIndex)));

                    supplementScheduleRepository.save(supplementSchedule.get(scheduleIndex)); // 가독성을 위한 코드(없어도 동작함)
//                    supplementScheduleRepository.save(supplementSchedule.get(scheduleIndex)); // 가독성 위한 작성(없어도 동작함)
                     scheduleIndex++;
                 }
             }
//            for (int i = 0; i < supplementSchedule.size(); i++) {
//                supplementSchedule.get(i).setDaysOfWeek(updateSupplementScheduleRequest.getDaysOfWeek().get(i));
//                supplementSchedule.get(i).setScheduledTime(LocalTime.parse(updateSupplementScheduleRequest.getScheduledTimes().get(i)));
//                supplementScheduleRepository.save(supplementSchedule.get(i)); // 가독성 위한 작성(없어도 동작함)
        }

        return UpdateSupplementScheduleResponse.builder()
                .supplementId(supplementId)
                .daysOfWeek(updateSupplementScheduleRequest.getDaysOfWeek())
                .scheduledTime(updateSupplementScheduleRequest.getScheduledTimes())
                .build();
    }

    // 영양제 별 알림 여부 수정
    @Override
    @Transactional
    public void updateSupplementNotification(Long supplementId, Boolean isNotification) {

        Supplement supplement = supplementRepository.findById(supplementId).get();
        supplement.setNotificationEnabled(isNotification);
        supplementRepository.save(supplement); // 가독성 위해 작성
    }

    @Override
    @Transactional
    public void deleteSupplementSchedule(Long supplementId) {

        // 영양제 삭제일 업데이트
        Supplement supplement = supplementRepository.findById(supplementId).get();
        supplement.setDeletedAt(LocalDateTime.now());
        supplementRepository.save(supplement);

        // 영양제 관련된 스케줄 전부 삭제
        List<SupplementSchedule> supplementSchedules = supplementScheduleRepository.findBySupplementId(supplementId);
        supplementScheduleRepository.deleteAll(supplementSchedules);
    }

    @Override
    @Scheduled(cron = "0 */5 * * * *") // 5분마다 스케줄러 실행
//    @Scheduled(fixedRate = 10000)
    @Transactional
    public void createSupplementScheduleHistory() {

        List<SupplementSchedule> supplementSchedules = supplementScheduleRepository.findAll();

        for (SupplementSchedule schedule : supplementSchedules) {
            SupplementScheduleHistory history = SupplementScheduleHistory.builder()
                    .user(schedule.getUser())
                    .supplement(schedule.getSupplement())
                    .scheduledTime(LocalDateTime.of(LocalDate.now(), schedule.getScheduledTime()))
                    .status(Status.UNTAKEN) // 복용 아직X
                    .build();
            supplementScheduleHistoryRepository.save(history);
        }
        log.info("스케줄러 종료: SupplementScheduleHistory 생성 완료");
    }

    @Override
    public List<SuppelementScheduleListResponse> getSupplementList(int month, int day) {

       // 요청 날짜의 요일 정보 추출
        LocalDate nowDate = LocalDate.of(LocalDate.now().getYear(), month, day);

        // nowDate 날의 히스토리 정보 리스트 반환
        List<SupplementScheduleHistory> supplementScheduleHistory = supplementScheduleHistoryRepository.findByScheduledDate(nowDate);

        List<SuppelementScheduleListResponse> suppelementScheduleListResponses = new ArrayList<>();
        // 선택한 날짜의 히스토리 리스트 저장해서 반환
        for (SupplementScheduleHistory scheduleHistory : supplementScheduleHistory) {
            Supplement supplement = scheduleHistory.getSupplement();

            suppelementScheduleListResponses.add(SuppelementScheduleListResponse.builder()
                    .supplementName(supplement.getName())
                    .scheduleTime(scheduleHistory.getScheduledTime())
                    .takenAt(scheduleHistory.getTakenAt())
                    .status(scheduleHistory.getStatus())
                    .build());
        }
        return suppelementScheduleListResponses;
    }

    @Override
    public List<SupplementResponse> getMySupplementList(Long userId) {
        return supplementScheduleRepository.findSupplementsByUserId(userId);
    }
}
