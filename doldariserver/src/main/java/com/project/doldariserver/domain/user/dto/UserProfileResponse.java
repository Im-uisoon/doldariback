package com.project.doldariserver.domain.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserProfileResponse {
    private String regionName;
    private String siteName;
    private String name;
    private String phone;
    private String role;
    private int entryBlock;

    public UserProfileResponse(String regionName, String siteName, String name, String phone, String role, int entryBlock) {
        this.regionName = regionName;
        this.siteName = siteName;
        this.name = name;
        this.phone = phone;
        this.role = role;
        this.entryBlock = entryBlock;
    }
}
