package com.project.doldariserver.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DailyTrainingStatusDto {
    private String taskName;
    private String trainingCode;
    private boolean isDone;
}
