package com.example.task_manager_ui.service;

import com.example.task_manager_ui.entity.Task;
import com.example.task_manager_ui.enums.Priority;
import com.example.task_manager_ui.enums.Status;
import com.example.task_manager_ui.model.TaskDto;
import com.example.task_manager_ui.repository.TaskRepository;
import com.example.task_manager_ui.utils.DateTimeHelper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TaskService {

    public static final int PAGE_SIZE = 12;

    private static final String DUE_DATE = "dueDate";

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

    public void changeStatusToComplete(long id) {
        Task task = taskRepository.findById(id).get();

        task.setStatus(Status.COMPLETED);
        Task savedTask = taskRepository.save(task);
    }

    public void deleteTaskById(long id) {
        taskRepository.deleteById(id);
    }

    public Page<TaskDto> findSortedTasks(String priority, String direction, int page) {
        Sort sort = Sort.by(Sort.Direction.fromString(direction), DUE_DATE);
        Pageable pageable = PageRequest.of(page - 1, PAGE_SIZE, sort);

        if (priority != null) {
            Priority enumPriority = Priority.valueOf(priority.toUpperCase());
            Page<Task> taskPage = taskRepository.findByStatusAndPriority(Status.NEW, enumPriority, pageable);

            List<TaskDto> retrievedTasks = taskPage.getContent()
                    .stream()
                    .map(this::convertToDto)
                    .toList();

            return new PageImpl<>(retrievedTasks, pageable, taskPage.getTotalElements());
        }
        Page<Task> taskPage = taskRepository.findByStatus(Status.NEW, pageable);

        List<TaskDto> retrievedTasks = taskPage.getContent()
                .stream()
                .map(this::convertToDto)
                .toList();

        return new PageImpl<>(retrievedTasks, pageable, taskPage.getTotalElements());
    }

    public Page<TaskDto> findTasksByTitle(String query, int page) {
        Sort sort = Sort.by(Sort.Direction.fromString("asc"), DUE_DATE);
        Pageable pageable = PageRequest.of(page - 1, PAGE_SIZE, sort);

        Page<Task> taskPage = taskRepository.findByStatusAndTitleStartsWithIgnoreCase(Status.NEW, query, pageable);

        List<TaskDto> retrievedTasks = taskPage.getContent()
                .stream()
                .map(this::convertToDto)
                .toList();

        return new PageImpl<>(retrievedTasks, pageable, taskPage.getTotalElements());
    }

    private TaskDto convertToDto(Task task) {
        return new TaskDto(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getPriority(),
                task.getDueDate(),
                DateTimeHelper.calculateRemainingDays(task.getDueDate())
        );
    }
}
