package com.project.doldariserver.domain.login.service;

import com.project.doldariserver.domain.user.entity.Admin;
import com.project.doldariserver.domain.user.repository.AdminRepository;
import com.project.doldariserver.domain.login.dto.AdminLoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminService {

    private final AdminRepository adminRepository;

    // 관리자 로그인
    @Transactional
    public boolean login(AdminLoginRequest request) {
        Admin admin = adminRepository.findById(request.getId())
                .orElse(null);

        if (admin == null) {
            return false;
        }
        return admin.getPassword().equals(request.getPassword());
    }
}
