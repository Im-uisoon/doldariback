package com.project.doldariserver.domain.user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "admin")
@Getter
@Setter
public class Admin {

    @Id
    @Column(name = "id", length = 50)
    private String id;

    @Column(name = "password", nullable = false, length = 100)
    private String password;
}
