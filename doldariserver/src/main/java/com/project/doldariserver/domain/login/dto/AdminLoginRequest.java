package com.project.doldariserver.domain.login.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminLoginRequest {
    private String id;
    private String password;
}
