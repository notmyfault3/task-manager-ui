package com.example.task_manager_ui.controller;

import com.example.task_manager_ui.enums.Priority;
import com.example.task_manager_ui.model.TaskDto;
import com.example.task_manager_ui.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class DashboardController {

    private final TaskService taskService;

    @GetMapping("/dashboard")
    public String getDashboardTasks(
            @RequestParam(value = "priority", required = false) String priority,
            @RequestParam(value = "direction", required = false, defaultValue = "asc") String direction,
            Model model) {

        List<TaskDto> tasks = taskService.findSortedTasks(priority, direction);
        model.addAttribute("tasks", tasks);
        model.addAttribute("priorities", Priority.values());
        return "dashboard";
    }

}
