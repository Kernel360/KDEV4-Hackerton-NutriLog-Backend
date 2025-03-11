package com.nutrilog.nutrilog_backend.notification.service.impl;

import com.nutrilog.nutrilog_backend.common.entities.FcmToken;
import com.nutrilog.nutrilog_backend.common.entities.User;
import com.nutrilog.nutrilog_backend.notification.repository.FcmTokenRepository;
import com.nutrilog.nutrilog_backend.notification.service.FcmTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FcmTokenServiceImpl implements FcmTokenService {

    private final FcmTokenRepository fcmTokenRepository;

    @Override
    @Transactional
    public void registerToken(User userDetails, String token) {
        log.info("Registering FCM token: {}", token);

        // 기존에 저장된 토큰을 조회 (삭제되지 않은 것만)
        List<FcmToken> existingTokens = fcmTokenRepository.findByUserAndDeletedFalse(userDetails);

        // 기존 토큰이 있는 경우에만 소프트 딜리트 처리
        if (!existingTokens.isEmpty()) {
            log.info("기존에 발급된 토큰 개수: {}", existingTokens.size());
            existingTokens.forEach(fcmToken -> {
                fcmToken.softDelete();
                fcmTokenRepository.save(fcmToken);
            });
            log.info("기존 토큰을 소프트 딜리트 처리 완료");
        } else {
            log.info("기존에 저장된 FCM 토큰이 없음");
        }

        // 새로운 FCM 토큰 저장
        FcmToken newFcmToken = FcmToken.builder()
                .user(userDetails)
                .token(token)
                .deleted(false)
                .build();
        fcmTokenRepository.save(newFcmToken);

        log.info("새로운 FCM 토큰 저장 완료");
    }

    @Override
    @Transactional
    public void deleteToken(User userDetails) {
        // 유저의 삭제되지 않은 모든 FCM 토큰 조회
        List<FcmToken> fcmTokens = fcmTokenRepository.findByUserAndDeletedFalse(userDetails);

        // 기존 토큰이 없는 경우 로그 출력 후 종료
        if (fcmTokens.isEmpty()) {
            log.info("삭제할 FCM 토큰이 없습니다. User ID: {}", userDetails.getId());
            return;
        }

        // 기존 토큰을 소프트 딜리트 처리
        log.info("User ID: {}의 FCM 토큰 {}개를 소프트 딜리트 처리합니다.", userDetails.getId(), fcmTokens.size());
        fcmTokens.forEach(fcmToken -> {
            fcmToken.softDelete();
            fcmTokenRepository.save(fcmToken);
        });

        log.info("User ID: {}의 모든 FCM 토큰이 소프트 딜리트 처리되었습니다.", userDetails.getId());
    }
}

