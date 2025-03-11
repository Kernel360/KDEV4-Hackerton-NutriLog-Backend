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
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Builder
@Setter
@Getter
public class SupplementSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // @ManyToOne
    // private User user;

    @ManyToOne
    @JoinColumn(name = "supplement_id")
    private Supplement supplement;

    @Enumerated(EnumType.STRING)
    private DaysOfWeek daysOfWeek;
    private LocalTime scheduledTime;
}
