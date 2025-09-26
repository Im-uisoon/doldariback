package com.project.doldariserver.domain.task.controller;

import com.project.doldariserver.domain.task.dto.TaskCreationRequest;
import com.project.doldariserver.domain.task.service.TaskManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/task")
@RequiredArgsConstructor
public class TaskController {

    private final TaskManagementService taskManagementService;

    @PostMapping
    public ResponseEntity<String> createDailyTasks(@RequestBody TaskCreationRequest request) {
        try {
            taskManagementService.createDailyTasks(request);
            return ResponseEntity.status(HttpStatus.CREATED).body("일일 교육을 이수했습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("문제가 발생했습니다 : " + e.getMessage());
        }
    }
}
