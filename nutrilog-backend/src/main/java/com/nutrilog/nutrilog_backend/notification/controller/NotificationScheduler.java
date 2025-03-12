//package com.nutrilog.nutrilog_backend.notification.controller;
//
//import com.nutrilog.nutrilog_backend.notification.service.FcmService;
//import com.nutrilog.nutrilog_backend.supplement.Status;
//import com.nutrilog.nutrilog_backend.supplement.entity.SupplementScheduleHistory;
//import com.nutrilog.nutrilog_backend.notification.repository.FcmTokenRepository;
//import com.nutrilog.nutrilog_backend.common.entities.FcmToken;
//import com.nutrilog.nutrilog_backend.common.entities.User;
//import com.nutrilog.nutrilog_backend.supplement.repository.SupplementScheduleHistoryRepository;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.time.LocalTime;
//import java.util.List;
//
//@Slf4j
//@Component
//@RequiredArgsConstructor
//public class NotificationScheduler {
//
//    private final SupplementScheduleHistoryRepository scheduleHistoryRepository;
//    private final FcmTokenRepository fcmTokenRepository;
//    private final FcmService fcmTokenService;  // FCM 알림 서비스
//
//    /**
//     * 매일 오전 8시, 12시, 18시에 푸시 알림을 보낸다.
//     */
//    @Scheduled(cron = "0 0 8,12,18 * * ?") // 오전 8시, 12시, 18시 실행
//    public void sendSupplementNotifications() {
//        log.info("🔔 푸시 알림 스케줄러 실행됨");
//
//        // 오늘 날짜 기준으로 스케줄 조회
//        LocalDate today = LocalDate.now();
//        LocalDateTime startOfDay = today.atStartOfDay(); // 00:00:00
//        LocalDateTime endOfDay = today.atTime(LocalTime.MAX); // 23:59:59
//        List<SupplementScheduleHistory> todaySchedules = scheduleHistoryRepository
//                .findByScheduledTimeBetweenAndStatus(startOfDay, endOfDay, Status.UNTAKEN);
//        log.info("{}", todaySchedules.toString());
//
//        if (todaySchedules.isEmpty()) {
//            log.info("✅ 오늘 복용할 보충제 일정(미복용 상태)이 없습니다.");
//            return;
//        }
//
//        for (SupplementScheduleHistory schedule : todaySchedules) {
//            User user = schedule.getUser();
//
//            // 유저의 FCM 토큰 조회 (삭제되지 않은 토큰만)
//            List<FcmToken> userTokens = fcmTokenRepository.findByUserAndDeletedFalse(user);
//            if (userTokens.isEmpty()) {
//                log.warn("⚠️ 유저 {} 에게 활성화된 FCM 토큰이 없습니다.", user.getId());
//                continue;
//            }
//
//            // 푸시 알림 전송
//            for (FcmToken token : userTokens) {
//                String title = "영양제 복용 알림 📢";
//                String body = String.format("%s 복용할 시간이에요!", schedule.getSupplement().getName());
//
////                boolean success = fcmTokenService.sendPushNotification(token.getToken(), title, body);
////                if (success) {
////                    log.info("✅ FCM 푸시 알림 전송 성공: 유저={}, 토큰={}", user.getId(), token.getToken());
////                } else {
////                    log.error("❌ FCM 푸시 알림 전송 실패: 유저={}, 토큰={}", user.getId(), token.getToken());
////                }
//            }
//        }
//    }
//}
