package com.example.task_manager_ui.repository;

import com.example.task_manager_ui.entity.Task;
import com.example.task_manager_ui.enums.Priority;
import com.example.task_manager_ui.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    Page<Task> findByStatusAndPriority(Status status, Priority priority, Pageable pageable);

    Page<Task> findByStatus(Status status, Pageable pageable);

    Page<Task> findByStatusAndTitleStartsWithIgnoreCase(Status status, String query, Pageable pageable);
}
