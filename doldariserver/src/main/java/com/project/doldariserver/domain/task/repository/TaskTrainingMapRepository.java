package com.project.doldariserver.domain.task.repository;

import com.project.doldariserver.domain.task.entity.TaskTrainingMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskTrainingMapRepository extends JpaRepository<TaskTrainingMap, Long> {
    List<TaskTrainingMap> findByTaskType_Id(Long taskTypeId);
}
