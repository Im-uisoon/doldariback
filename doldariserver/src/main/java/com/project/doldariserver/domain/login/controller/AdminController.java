package com.project.doldariserver.domain.login.controller;

import com.project.doldariserver.domain.login.dto.AdminLoginRequest;
import com.project.doldariserver.domain.login.service.AdminService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    // 관리자 로그인
    @PostMapping("/admin-login")
    public ResponseEntity<String> adminLogin(@RequestBody AdminLoginRequest request, HttpSession session) {
        boolean isAuthenticated = adminService.login(request);

        // 로그인 후 세션 저장 (8시간)
        if (isAuthenticated) {
            session.setAttribute("adminId", request.getId());
            session.setMaxInactiveInterval(28800);
            return ResponseEntity.ok("관리자 로그인 성공");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("관리자 로그인에 실패했습니다");
        }
    }

    // 로그아웃 (세션 만료)
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok("로그아웃 성공");
    }
}
