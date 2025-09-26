package com.project.doldariserver.domain.place.dto;

import com.project.doldariserver.domain.place.entity.Site;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SiteDto {
    private Long id;
    private String name;
    private RegionDto region; // Nested DTO

    public SiteDto(Site site) {
        this.id = site.getId();
        this.name = site.getName();
        // Ensure region is not null before accessing it
        if (site.getRegion() != null) {
            this.region = new RegionDto(site.getRegion());
        }
    }
}
