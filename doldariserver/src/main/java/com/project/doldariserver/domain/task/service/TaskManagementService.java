package com.project.doldariserver.domain.task.service;

import com.project.doldariserver.domain.task.dto.TaskCreationRequest;
import com.project.doldariserver.domain.task.entity.DailyTraining;
import com.project.doldariserver.domain.task.entity.Task;
import com.project.doldariserver.domain.task.entity.TaskType;
import com.project.doldariserver.domain.task.entity.TaskTrainingMap;
import com.project.doldariserver.domain.task.repository.DailyTrainingRepository;
import com.project.doldariserver.domain.task.repository.TaskRepository;
import com.project.doldariserver.domain.task.repository.TaskTypeRepository;
import com.project.doldariserver.domain.task.repository.TaskTrainingMapRepository;
import com.project.doldariserver.domain.user.entity.User;
import com.project.doldariserver.domain.user.repository.UserRepository;
import com.project.doldariserver.domain.place.entity.Site; // Import Site entity
import com.project.doldariserver.domain.place.repository.SiteRepository; // Import SiteRepository

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class TaskManagementService {

    private final TaskTypeRepository taskTypeRepository;
    private final TaskRepository taskRepository;
    private final DailyTrainingRepository dailyTrainingRepository;
    private final UserRepository userRepository;
    private final SiteRepository siteRepository; // To fetch Site entity
    private final TaskTrainingMapRepository taskTrainingMapRepository;

    public void createDailyTasks(TaskCreationRequest request) {
        Optional<Site> siteOptional = siteRepository.findById(request.getSiteId());
        if (siteOptional.isEmpty()) {
            throw new IllegalArgumentException("에러 : 사이트를 찾을 수 없습니다" + request.getSiteId());
        }
        Site site = siteOptional.get();

        List<Task> createdTasks = new ArrayList<>();

        for (String taskName : request.getTasks()) {
            // 1) tasks 배열 안의 작업명을 task_type 테이블에서 조회
            TaskType taskType = taskTypeRepository.findByName(taskName)
                    .orElseGet(() -> {
                        TaskType newType = new TaskType();
                        newType.setName(taskName);
                        return taskTypeRepository.save(newType);
                    });

            // 2) task 테이블에 (site_id, task_type_id, task_date) INSERT
            Task task = new Task();
            task.setSite(site);
            task.setTaskType(taskType);
            task.setTaskDate(request.getTaskDate());
            createdTasks.add(taskRepository.save(task));
        }

        // 3) 해당 siteId에 소속된 user들을 불러와서, 각 task별로 daily_training 테이블에 INSERT
        List<User> usersInSite = userRepository.findBySiteId(request.getSiteId());

        for (Task task : createdTasks) {
            List<TaskTrainingMap> trainingMaps = taskTrainingMapRepository.findByTaskType_Id(task.getTaskType().getId());

            if (trainingMaps.isEmpty()) {
                throw new IllegalArgumentException("받을 훈련이 없습니다.: " + task.getTaskType().getName());
            }
            // 작업당 훈련 코드
            for (User user : usersInSite) {
                for (TaskTrainingMap map : trainingMaps) {
                    DailyTraining dailyTraining = new DailyTraining();
                    dailyTraining.setUser(user);
                    dailyTraining.setTask(task);
                    dailyTraining.setTrainingCode(map.getTrainingCode());
                    dailyTraining.setDone(false);
                    dailyTraining.setDoneDate(null);
                    dailyTrainingRepository.save(dailyTraining);
                }
            }
        }
    }
}
