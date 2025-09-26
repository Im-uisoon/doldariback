package com.project.doldariserver.domain.task.repository;

import com.project.doldariserver.domain.task.entity.DailyTraining;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DailyTrainingRepository extends JpaRepository<DailyTraining, Long> {
    List<DailyTraining> findByUser_IdAndTask_TaskDate(Long userId, LocalDate taskDate);
}
