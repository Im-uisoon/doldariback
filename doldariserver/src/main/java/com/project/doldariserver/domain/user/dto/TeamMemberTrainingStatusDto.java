package com.project.doldariserver.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class TeamMemberTrainingStatusDto {
    private Long userId;
    private String userName;
    private List<DailyTrainingStatusDto> dailyTrainings;
}
