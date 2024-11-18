package com.example.task_manager_ui.controller;

import com.example.task_manager_ui.model.TaskDto;
import com.example.task_manager_ui.service.TaskService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/dashboard")
public class DashboardController {

    private final TaskService taskService;

    @GetMapping
    public String viewDashboardByPage(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "priority", required = false) String priority,
            @RequestParam(value = "direction", required = false, defaultValue = "asc") String direction,
            HttpServletRequest request, HttpSession session, Model model) {

        String path = request.getRequestURI();
        StringBuilder previousPath = new StringBuilder(path);
        if (request.getQueryString() != null) {
            previousPath.append("?%s".formatted(request.getQueryString()));
        }
        session.setAttribute("previousPath", previousPath.toString());

        Page<TaskDto> tasks = taskService.findSortedTasks(priority, direction, page);

        model.addAttribute("tasks", tasks);
        model.addAttribute("totalPages", tasks.getTotalPages());
        model.addAttribute("path", path);
        model.addAttribute("page", page);
        model.addAttribute("priority", priority);
        model.addAttribute("direction", direction);

        return "dashboard";
    }

    @GetMapping("/search")
    public String searchTasksByTitle(@RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                     @RequestParam("query") String query, Model model) {

        Page<TaskDto> tasks = taskService.findTasksByTitle(query, page);

        model.addAttribute("tasks", tasks);
        model.addAttribute("totalPages", tasks.getTotalPages());
        model.addAttribute("page", page);
        model.addAttribute("query", query);

        return "dashboard";
    }
}
