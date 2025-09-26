package com.project.doldariserver.domain.place.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SiteCreationRequest {
    private String name;
    private Long regionId;
}
