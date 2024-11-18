package com.example.task_manager_ui.model;

import com.example.task_manager_ui.enums.Priority;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

    @NotBlank(message = "The title should not be blank")
    @Size(min = 2, max = 30, message = "The length of the title should be from 2 to 30 characters")
    private String title;

    @NotBlank(message = "The description should not be blank")
    private String description;

    private Priority priority;

    @NotNull(message = "The deadline should be specified")
    @Future(message = "The deadline for the task must be after the current time")
    private LocalDateTime dueDate;

    private long daysRemaining;
}
