package com.nutrilog.nutrilog_backend.notification.repository;

import com.nutrilog.nutrilog_backend.common.entities.FcmToken;
import com.nutrilog.nutrilog_backend.common.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FcmTokenRepository extends JpaRepository<FcmToken, Long> {
    // 유저의 삭제되지 않은 모든 토큰을 가져오는 메서드
    List<FcmToken> findByUserAndDeletedFalse(User user);
}
