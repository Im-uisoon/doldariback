package com.project.doldariserver.domain.login.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginRequest {
    private String name;
    private String phone;
    private Long siteId;
    private Long regionId;
}
