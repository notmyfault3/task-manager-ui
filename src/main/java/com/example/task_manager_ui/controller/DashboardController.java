package com.example.task_manager_ui.controller;

import com.example.task_manager_ui.model.TaskDto;
import com.example.task_manager_ui.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class DashboardController {

    private final TaskService taskService;

    @GetMapping("/dashboard")
    public String dashboardPage(Model model) {
        List<TaskDto> tasks = taskService.findAllTasks();
        model.addAttribute("tasks", tasks);
        return "dashboard";
    }
}
