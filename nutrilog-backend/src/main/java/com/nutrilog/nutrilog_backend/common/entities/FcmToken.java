package com.nutrilog.nutrilog_backend.common.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class FcmToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id = null;

    @Column(length = 255, nullable = false)
    private String token;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // 사용자 정보 (User 엔티티와 연결)

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean deleted;

    public void softDelete() {
        this.deleted = true;
    }
}
