package com.example.task_manager_ui.advice;

import com.example.task_manager_ui.enums.Priority;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalModelAttributes {

    @ModelAttribute("priorities")
    public Priority[] getPriorities() {
        return Priority.values();
    }
}
