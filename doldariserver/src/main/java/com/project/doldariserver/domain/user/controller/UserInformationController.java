package com.project.doldariserver.domain.user.controller;

import com.project.doldariserver.domain.user.dto.*;
import com.project.doldariserver.domain.user.service.UserInformationService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserInformationController {

    private final UserInformationService userInformationService;

    // 유저 교육 + 시험 상태 확인
    @GetMapping("/education")
    public ResponseEntity<UserEducationResponse> getUserEducation(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        UserEducationResponse response = userInformationService.getUserEducationInfo(userId);
        return ResponseEntity.ok(response);
    }

    // 오늘 할 일 목록 조회
    @GetMapping("/today")
    public ResponseEntity<List<UserDailyTaskResponse>> getTodayTasks(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        List<UserDailyTaskResponse> response = userInformationService.getTodayDailyTasks(userId);
        return ResponseEntity.ok(response);
    }

    // 일일 교육 완료 처리
    @PostMapping("/today/complete")
    public ResponseEntity<String> completeTodayTask(@RequestBody DailyTrainingCompletionRequest request, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("다시 로그인해주세요");
        }

        boolean completed = userInformationService.completeDailyTraining(userId, request.getDailyTrainingId());

        if (completed) {
            return ResponseEntity.ok("일일 교육을 완료했습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("일일 교육에 실패했습니다.");
        }
    }

    // 유저 프로필 조회 및 entryBlock 계산
    @GetMapping("/profile")
    public ResponseEntity<UserProfileResponse> getUserProfile(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // 비로그인 차단
        }

        try {
            UserProfileResponse response = userInformationService.getUserProfile(userId);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 비회원 차단
        }
    }

    // 교육정보 업데이트
    @PostMapping("/education-learning")
    public ResponseEntity<String> updateUserEducation(@RequestBody EducationLearningRequest request, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("다시 로그인해주세요");
        }
        userInformationService.updateUserEducation(userId, request);
        return ResponseEntity.ok("안전 교육 정보가 업데이트되었습니다.");
    }

    // 시험정보 업데이트
    @PostMapping("/education-test")
    public ResponseEntity<String> updateUserTest(@RequestBody EducationTestRequest request, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("다시 로그인해주세요");
        }
        userInformationService.updateUserTest(userId, request);
        return ResponseEntity.ok("안전 시험 정보가 업데이트되었습니다.");
    }
}
