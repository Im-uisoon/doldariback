package com.project.doldariserver.domain.user.repository;

import com.project.doldariserver.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByPhone(String phone);

    List<User> findBySiteId(Long siteId);

    Optional<User> findByNameAndPhoneAndSite_Id(String name, String phone, Long siteId);
}
