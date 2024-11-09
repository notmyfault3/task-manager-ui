package com.example.task_manager_ui.controller;

import com.example.task_manager_ui.model.TaskDto;
import com.example.task_manager_ui.service.TaskService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/dashboard")
public class DashboardController {

    private final TaskService taskService;

    @GetMapping
    public String getDashboardTasks(
            @RequestParam(value = "priority", required = false) String priority,
            @RequestParam(value = "direction", required = false, defaultValue = "asc") String direction,
            HttpServletRequest request, HttpSession session, Model model) {

        StringBuilder previousPath = new StringBuilder(request.getRequestURI());
        if (request.getQueryString() != null) {
            previousPath.append("?%s".formatted(request.getQueryString()));
        }
        session.setAttribute("previousPath", previousPath.toString());

        List<TaskDto> tasks = taskService.findSortedTasks(priority, direction);
        model.addAttribute("tasks", tasks);

        return "dashboard";
    }

    @GetMapping("/search")
    public String searchTasksByTitle(@RequestParam("query") String query, Model model) {
        List<TaskDto> foundTasks = taskService.findTasksByTitle(query);

        model.addAttribute("tasks", foundTasks);

        return "dashboard";
    }
}
