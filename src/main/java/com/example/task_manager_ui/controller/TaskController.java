package com.example.task_manager_ui.controller;

import com.example.task_manager_ui.enums.Priority;
import com.example.task_manager_ui.model.TaskDto;
import com.example.task_manager_ui.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    @GetMapping("/create-task")
    public String createNewTaskPage(Model model) {
        model.addAttribute("task", new TaskDto());
        model.addAttribute("priorities", Priority.values());
        return "create-task";
    }

    @PostMapping("/save-task")
    public String saveTask(@ModelAttribute(name = "task") TaskDto taskDto) {
        TaskDto savedTask = taskService.saveTask(taskDto);
        return "redirect:/tasks/create-task";
    }

}
