package com.nutrilog.nutrilog_backend.supplement.entity;

import java.time.LocalTime;
import java.util.List;

import com.nutrilog.nutrilog_backend.common.entities.User;
import com.nutrilog.nutrilog_backend.supplement.DaysOfWeek;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SupplementSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

     @ManyToOne
     @JoinColumn(name = "user_id")
     private User user;

    @ManyToOne
    @JoinColumn(name = "supplement_id")
    private Supplement supplement;

    @Enumerated(EnumType.STRING)
    private DaysOfWeek daysOfWeek;
    private LocalTime scheduledTime;
}
