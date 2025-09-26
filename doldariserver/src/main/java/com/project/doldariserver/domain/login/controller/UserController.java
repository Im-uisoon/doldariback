package com.project.doldariserver.domain.login.controller;

import com.project.doldariserver.domain.login.dto.UserLoginRequest;
import com.project.doldariserver.domain.login.service.UserService;
import com.project.doldariserver.domain.user.entity.User;
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
public class UserController {

    private final UserService userService;

    // 유저 로그인
    @PostMapping("/login")
    public ResponseEntity<String> userLogin(@RequestBody UserLoginRequest request, HttpSession session) {
        User authenticatedUser = userService.login(request);

        // 유저 로그인 (8시간)
        if (authenticatedUser != null) {
            session.setAttribute("userId", authenticatedUser.getId());
            session.setAttribute("user", authenticatedUser);
            session.setMaxInactiveInterval(28800);
            return ResponseEntity.ok("로그인 성공");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인에 실패했습니다");
        }
    }
}
