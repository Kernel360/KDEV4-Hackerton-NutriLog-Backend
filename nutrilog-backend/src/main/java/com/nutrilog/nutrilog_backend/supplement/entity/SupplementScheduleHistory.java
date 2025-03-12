package com.nutrilog.nutrilog_backend.supplement.entity;

import java.time.LocalDateTime;

import com.nutrilog.nutrilog_backend.common.entities.User;
import com.nutrilog.nutrilog_backend.supplement.Status;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class SupplementScheduleHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "supplement_id")
    private Supplement supplement;

    private LocalDateTime takenAt;
    private LocalDateTime scheduledTime;

    @Enumerated(EnumType.STRING)
    private Status status;
}
