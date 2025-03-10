package com.nutrilog.nutrilog_backend.common.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "user")
public class User extends BaseEntity {
    @Column(unique = true, nullable = false)
    private String socialKey; // 사용자 고유 식별 키

    @Column(nullable = false)
    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private UserType type; // KAKAO, GOOGLE, GUEST

    private boolean notificationEnabled = false;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;  // 엔티티가 생성될 때 자동으로 설정

    @Column(nullable = false)
    private LocalDateTime updatedAt;  // 엔티티가 수정될 때 자동으로 갱신

    @Column(nullable = true)
    private LocalDateTime deletedAt;  // 삭제된 시간

    // 엔티티 처음 저장될 때 자동 설정
    @PrePersist
    public void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    // 엔티티 수정될 때마다 자동 갱신
    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

}
