package com.project.doldariserver.domain.user.dto;

import com.project.doldariserver.domain.user.entity.User;
import com.project.doldariserver.domain.user.entity.UserRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    private Long id;
    private String name;
    private String phone;
    private UserRole role;
    private Long siteId;

    public UserDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.phone = user.getPhone();
        this.role = user.getRole();
        this.siteId = user.getSite().getId();
    }
}
