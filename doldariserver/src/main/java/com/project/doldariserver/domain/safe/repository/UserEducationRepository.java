package com.project.doldariserver.domain.safe.repository;

import com.project.doldariserver.domain.safe.entity.UserEducation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserEducationRepository extends JpaRepository<UserEducation, Long> {
    Optional<UserEducation> findByUser_Id(Long userId);
}
