package com.project.doldariserver.domain.user.dto;

import com.project.doldariserver.domain.safe.entity.UserEducationStatus;
import com.project.doldariserver.domain.safe.entity.UserTestStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserEducationResponse {
    private UserTestStatus test;
    private LocalDate testPassDate;
    private UserEducationStatus education;
    private LocalDate educationDate;

    public UserEducationResponse(UserTestStatus test, LocalDate testPassDate,
                                 UserEducationStatus education, LocalDate educationDate) {
        this.test = test;
        this.testPassDate = testPassDate;
        this.education = education;
        this.educationDate = educationDate;
    }
}
