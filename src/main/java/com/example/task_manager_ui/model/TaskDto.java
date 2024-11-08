package com.example.task_manager_ui.model;

import com.example.task_manager_ui.enums.Priority;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {

    private long id;

    private String title;

    private String description;

    private Priority priority;

    private LocalDateTime dueDate;

    private long daysRemaining;
}
