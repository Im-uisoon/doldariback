package com.project.doldariserver.domain.task.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "task_training_map")
@Getter
@Setter
public class TaskTrainingMap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_type_id", nullable = false)
    private TaskType taskType;

    @Column(name = "training_code", nullable = false, length = 50)
    private String trainingCode;
}
