package com.project.doldariserver.domain.user.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class EducationTestRequest {
    private String test;
    private LocalDate testPassDate;
}
