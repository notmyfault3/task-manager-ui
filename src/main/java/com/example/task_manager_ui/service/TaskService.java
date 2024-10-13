package com.example.task_manager_ui.service;

import com.example.task_manager_ui.entity.Task;
import com.example.task_manager_ui.enums.Status;
import com.example.task_manager_ui.model.TaskDto;
import com.example.task_manager_ui.repository.TaskRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskDto saveTask(TaskDto taskDto) {
        Task task = new Task();

        task.setTitle(taskDto.getTitle());
        task.setDescription(taskDto.getDescription());
        task.setStatus(Status.NEW);
        task.setPriority(taskDto.getPriority());
        task.setDueDate(taskDto.getDueDate());

        Task savedTask = taskRepository.save(task);

        return convertToDto(savedTask);
    }

    public List<TaskDto> findAllTasks() {
        return taskRepository.findAll().stream()
                .map(this::convertToDto)
                .toList();
    }

    private TaskDto convertToDto(Task task) {
        return new TaskDto(
                task.getTitle(),
                task.getDescription(),
                task.getPriority(),
                task.getDueDate()
        );
    }
}
