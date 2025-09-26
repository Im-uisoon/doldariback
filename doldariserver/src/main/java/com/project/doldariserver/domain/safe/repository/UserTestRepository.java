package com.project.doldariserver.domain.safe.repository;

import com.project.doldariserver.domain.safe.entity.UserTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserTestRepository extends JpaRepository<UserTest, Long> {
    Optional<UserTest> findByUser_Id(Long userId);
}
