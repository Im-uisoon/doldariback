package com.project.doldariserver.domain.user.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserDailyTaskResponse {
    private Long dailyTrainingId; // 어떤 교육 받는지 불러오기
    private String taskName;
    private String trainingCode;
    private boolean isDone;
    private LocalDate doneDate;

    public UserDailyTaskResponse(Long dailyTrainingId, String taskName, String trainingCode, boolean isDone, LocalDate doneDate) {
        this.dailyTrainingId = dailyTrainingId;
        this.taskName = taskName;
        this.trainingCode = trainingCode;
        this.isDone = isDone;
        this.doneDate = doneDate;
    }
}
