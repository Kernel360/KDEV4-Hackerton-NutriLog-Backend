package com.nutrilog.nutrilog_backend.supplement.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import com.nutrilog.nutrilog_backend.common.entities.User;
import com.nutrilog.nutrilog_backend.supplement.Status;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
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
    // @ManyToOne
    // @JoinColumn(name = "supplementSchedule_id")
    // private SupplementSchedule supplementSchedule;

    private LocalDateTime scheduledTime; // 복용 계획 시간
    private LocalDateTime takenAt; // 복용한 시간

    @Enumerated(EnumType.STRING)
    private Status status; // 복용 여부
    
}
