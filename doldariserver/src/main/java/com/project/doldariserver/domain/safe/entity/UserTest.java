package com.project.doldariserver.domain.safe.entity;

import com.project.doldariserver.domain.user.entity.User; // Import User entity
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate; // For DATE type

@Entity
@Table(name = "user_test")
@Getter
@Setter
public class UserTest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, columnDefinition = "ENUM('pass', 'fail', 'none') DEFAULT 'none'")
    private UserTestStatus status = UserTestStatus.none;

    @Column(name = "test_pass_date")
    private LocalDate testPassDate;
}
