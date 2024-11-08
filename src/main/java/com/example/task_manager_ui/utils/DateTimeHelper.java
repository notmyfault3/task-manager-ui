package com.example.task_manager_ui.utils;

import java.time.Duration;
import java.time.LocalDateTime;

public class DateTimeHelper {

    public static long calculateRemainingDays(LocalDateTime dueDate) {
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(now, dueDate);

        return duration.toDays();
    }
}
