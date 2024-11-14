package com.example.task_manager_ui.service;

import com.example.task_manager_ui.entity.Task;
import com.example.task_manager_ui.enums.Priority;
import com.example.task_manager_ui.enums.Status;
import com.example.task_manager_ui.model.TaskDto;
import com.example.task_manager_ui.repository.TaskRepository;
import com.example.task_manager_ui.utils.DateTimeHelper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final String dueDate = "dueDate";

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

    public List<TaskDto> findSortedTasks(String priority, String direction) {
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

    public List<TaskDto> findTasksByTitle(String query) {
        Sort sort = Sort.by(Sort.Direction.fromString("asc"), dueDate);
        List<Task> foundTasks = taskRepository.findByStatusAndTitleStartsWithIgnoreCase(Status.NEW, query, sort);

        return foundTasks.stream()
                .map(this::convertToDto)
                .toList();
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
