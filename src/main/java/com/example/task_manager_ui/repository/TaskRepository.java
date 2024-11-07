package com.example.task_manager_ui.repository;

import com.example.task_manager_ui.entity.Task;
import com.example.task_manager_ui.enums.Priority;
import com.example.task_manager_ui.enums.Status;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByStatusAndPriority(Status status, Priority priority, Sort sort);

    List<Task> findByStatus(Status status, Sort sort);
}
