package com.project.doldariserver.domain.place.dto;

import com.project.doldariserver.domain.place.entity.Region;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegionDto {
    private Long id;
    private String name;

    public RegionDto(Region region) {
        this.id = region.getId();
        this.name = region.getName();
    }
}