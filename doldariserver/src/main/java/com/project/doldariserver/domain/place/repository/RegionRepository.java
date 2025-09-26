package com.project.doldariserver.domain.place.repository;

import com.project.doldariserver.domain.place.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegionRepository extends JpaRepository<Region, Long> {
}
