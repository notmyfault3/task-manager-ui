package com.example.task_manager_ui.service;

import com.example.task_manager_ui.entity.Task;
import com.example.task_manager_ui.enums.Priority;
import com.example.task_manager_ui.enums.Status;
import com.example.task_manager_ui.model.TaskDto;
import com.example.task_manager_ui.repository.TaskRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskDto saveTask(TaskDto taskDto) {
        Task task = new Task();

        task.setId(taskDto.getId());
        task.setTitle(taskDto.getTitle());
        task.setDescription(taskDto.getDescription());
        task.setStatus(Status.NEW);
        task.setPriority(taskDto.getPriority());
        task.setDueDate(taskDto.getDueDate());

        Task savedTask = taskRepository.save(task);

        return convertToDto(savedTask);
    }

    public TaskDto findTaskById(long id) {
        Task task = taskRepository.findById(id).get();

        return convertToDto(task);
    }

    public List<TaskDto> findAllTasks() {
        return taskRepository.findAll().stream()
                .map(this::convertToDto)
                .toList();
    }

    public List<TaskDto> findSortedTasks(String priority, String direction) {
        String dueDate = "dueDate";
        Sort sort = Sort.by(Sort.Direction.fromString(direction), dueDate);

        if (priority != null) {
            Priority enumPriority = Priority.valueOf(priority.toUpperCase());

            return taskRepository.findByStatusAndPriority(Status.NEW, enumPriority, sort).stream()
                    .map(this::convertToDto)
                    .toList();
        }

        return taskRepository.findByStatus(Status.NEW, sort).stream()
                .map(this::convertToDto)
                .toList();
    }

    public void changeStatusToComplete(long id) {
        Task task = taskRepository.findById(id).get();

        task.setStatus(Status.COMPLETED);
        Task savedTask = taskRepository.save(task);
    }

    public void deleteTaskById(long id) {
        taskRepository.deleteById(id);
    }

    private TaskDto convertToDto(Task task) {
        return new TaskDto(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getPriority(),
                task.getDueDate()
        );
    }
}
