package com.task.task_service.repo;

import com.task.task_service.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task,Long> {

    List<Task> findByAssignedUserId(Long userId);

}