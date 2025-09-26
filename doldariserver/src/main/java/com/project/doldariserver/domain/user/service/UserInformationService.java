package com.project.doldariserver.domain.user.service;

import com.project.doldariserver.domain.user.dto.EducationLearningRequest;
import com.project.doldariserver.domain.user.dto.EducationTestRequest;
import com.project.doldariserver.domain.user.dto.UserEducationResponse;
import com.project.doldariserver.domain.user.dto.UserDailyTaskResponse;
import com.project.doldariserver.domain.user.dto.UserProfileResponse; // New import
import com.project.doldariserver.domain.safe.entity.UserTest;
import com.project.doldariserver.domain.safe.entity.UserEducation;
import com.project.doldariserver.domain.safe.repository.UserTestRepository;
import com.project.doldariserver.domain.safe.repository.UserEducationRepository;
import com.project.doldariserver.domain.safe.entity.UserTestStatus;
import com.project.doldariserver.domain.safe.entity.UserEducationStatus;
import com.project.doldariserver.domain.task.entity.DailyTraining;
import com.project.doldariserver.domain.task.repository.DailyTrainingRepository;
import com.project.doldariserver.domain.user.entity.User; // New import
import com.project.doldariserver.domain.user.repository.UserRepository; // New import
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserInformationService {

    private final UserTestRepository userTestRepository;
    private final UserEducationRepository userEducationRepository;
    private final DailyTrainingRepository dailyTrainingRepository;
    private final UserRepository userRepository; // New field

    // 유저 교육 상태 확인
    public UserEducationResponse getUserEducationInfo(Long userId) {
        UserTest userTest = userTestRepository.findByUser_Id(userId)
                .orElse(null);

        UserEducation userEducation = userEducationRepository.findByUser_Id(userId)
                .orElse(null);

        UserTestStatus testStatus = (userTest != null) ? userTest.getStatus() : UserTestStatus.none;
        LocalDate testDate = (userTest != null) ? userTest.getTestPassDate() : null;

        UserEducationStatus educationStatus = (userEducation != null) ? userEducation.getStatus() : UserEducationStatus.none;
        LocalDate educationDate = (userEducation != null) ? userEducation.getEducationDate() : null;

        return new UserEducationResponse(testStatus, testDate, educationStatus, educationDate);
    }

    // 오늘 할 일 목록 조회
    public List<UserDailyTaskResponse> getTodayDailyTasks(Long userId) {
        LocalDate today = LocalDate.now();
        List<DailyTraining> dailyTrainings = dailyTrainingRepository.findByUser_IdAndTask_TaskDate(userId, today);

        return dailyTrainings.stream()
                .map(dt -> new UserDailyTaskResponse(
                        dt.getId(),
                        dt.getTask().getTaskType().getName(),
                        dt.getTrainingCode(),
                        dt.isDone(),
                        dt.getDoneDate()
                ))
                .collect(Collectors.toList());
    }

    // 일일 교육 완료 처리
    @Transactional
    public boolean completeDailyTraining(Long userId, Long dailyTrainingId) {
        Optional<DailyTraining> dailyTrainingOptional = dailyTrainingRepository.findById(dailyTrainingId);

        // 교육 찾기 실패
        if (dailyTrainingOptional.isEmpty()) {
            return false;
        }
        DailyTraining dailyTraining = dailyTrainingOptional.get();

        // 비로그인 차단 + 다른 사용자 대리 완료 차단
        if (!dailyTraining.getUser().getId().equals(userId)) {
            return false;
        }

        // 완료 후 저장
        dailyTraining.setDone(true);
        dailyTraining.setDoneDate(LocalDate.now());
        dailyTrainingRepository.save(dailyTraining);
        return true;
    }

    // 유저 프로필 조회 및 entryBlock 계산
    public UserProfileResponse getUserProfile(Long userId) {
        // Fetch User details
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));

        // 계산을 위해 엔티티 호출
        Optional<UserEducation> userEducationOptional = userEducationRepository.findByUser_Id(userId);
        Optional<UserTest> userTestOptional = userTestRepository.findByUser_Id(userId);

        // 일일교육 (오늘 날짜 불러와야 함)
        LocalDate today = LocalDate.now();
        List<DailyTraining> todayDailyTrainings = dailyTrainingRepository.findByUser_IdAndTask_TaskDate(userId, today);

        int entryBlock = 0; // 기본값 0

        // Level 1: 안전교육 이수 상태
        if (userEducationOptional.isPresent()) {
            UserEducation userEducation = userEducationOptional.get();
            if (userEducation.getStatus() == UserEducationStatus.expire || userEducation.getStatus() == UserEducationStatus.none) {
                entryBlock = 1;
            }
        } else {
            entryBlock = 1;
        }

        // Level 2: 안전시험 패스 상태
        if (entryBlock == 0) {
            if (userTestOptional.isPresent()) {
                UserTest userTest = userTestOptional.get();
                if (userTest.getStatus() == UserTestStatus.fail || userTest.getStatus() == UserTestStatus.none) {
                    entryBlock = 2;
                }
            } else {
                entryBlock = 2;
            }
        }

        // Level 3: 일일 교육 이수 상태
        if (entryBlock == 0) {
            boolean allDailyTrainingsDone = todayDailyTrainings.stream()
                    .allMatch(DailyTraining::isDone);

            if (!todayDailyTrainings.isEmpty() && !allDailyTrainingsDone) {
                entryBlock = 3;
            }
        }

        return new UserProfileResponse(
                user.getSite().getRegion().getName(),
                user.getSite().getName(),
                user.getName(),
                user.getPhone(),
                user.getRole().name(),
                entryBlock
        );
    }

    // 교육상태 업데이트
    @Transactional
    public void updateUserEducation(Long userId, EducationLearningRequest request) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));
        UserEducation userEducation = userEducationRepository.findByUser_Id(userId).orElse(new UserEducation());

        userEducation.setUser(user);
        userEducation.setEducationDate(request.getEducationDate());
        userEducation.setStatus(UserEducationStatus.valueOf(request.getEducation()));

        userEducationRepository.save(userEducation);
    }

    // 시험통과 업데이트
    @Transactional
    public void updateUserTest(Long userId, EducationTestRequest request) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));
        UserTest userTest = userTestRepository.findByUser_Id(userId).orElse(new UserTest());

        userTest.setUser(user);
        userTest.setTestPassDate(request.getTestPassDate());
        userTest.setStatus(UserTestStatus.valueOf(request.getTest()));

        userTestRepository.save(userTest);
    }
}
