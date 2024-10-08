package com.example.task_manager_ui.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TaskController {

    @GetMapping("/create-task")
    public String createNewTaskPage() {
        return "create-task";
    }
}
