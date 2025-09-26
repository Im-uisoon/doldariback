package com.project.doldariserver.domain.user.dto;

import com.project.doldariserver.domain.user.entity.UserRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreationRequest {
    private String name;
    private String phone;
    private UserRole role;
    private Long siteId;
}
