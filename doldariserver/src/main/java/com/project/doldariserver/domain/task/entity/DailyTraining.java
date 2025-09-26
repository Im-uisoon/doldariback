package com.project.doldariserver.domain.task.entity;

import com.project.doldariserver.domain.user.entity.User; // Import User entity
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate; // For DATE type

@Entity
@Table(name = "daily_training")
@Getter
@Setter
public class DailyTraining {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;

    @Column(name = "training_code", nullable = false, length = 50)
    private String trainingCode;

    @Column(name = "is_done", nullable = false)
    private boolean isDone = false;

    @Column(name = "done_date")
    private LocalDate doneDate;
}
