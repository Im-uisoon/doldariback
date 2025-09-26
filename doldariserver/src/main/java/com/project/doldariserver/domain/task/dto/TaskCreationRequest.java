package com.project.doldariserver.domain.task.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class TaskCreationRequest {
    private Long siteId;
    private LocalDate taskDate;
    private List<String> tasks; // 작업 명
}
