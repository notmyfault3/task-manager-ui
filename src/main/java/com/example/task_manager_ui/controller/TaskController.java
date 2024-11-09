package com.example.task_manager_ui.controller;

import com.example.task_manager_ui.model.TaskDto;
import com.example.task_manager_ui.service.TaskService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    @GetMapping("/task-form")
    public String createNewTaskPage(Model model) {
        model.addAttribute("task", new TaskDto());

        return "task-form";
    }

    @PostMapping("/save-task")
    public String saveTask(@ModelAttribute(name = "task") TaskDto taskDto) {
        TaskDto savedTask = taskService.saveTask(taskDto);

        return "redirect:/tasks/task-form";
    }

    @GetMapping("/{id}/complete")
    public String completeTask(@PathVariable long id, HttpSession session) {
        String previousPath = session.getAttribute("previousPath").toString();
        taskService.changeStatusToComplete(id);

        return "redirect:%s".formatted(previousPath);
    }

    @GetMapping("/{id}/modify")
    public String modifyTask(@PathVariable long id, Model model) {
        TaskDto task = taskService.findTaskById(id);
        model.addAttribute("task", task);

        return "task-form";
    }

    @GetMapping("/{id}/delete")
    public String deleteTask(@PathVariable long id, HttpSession session) {
        String previousPath = session.getAttribute("previousPath").toString();
        taskService.deleteTaskById(id);

        return "redirect:%s".formatted(previousPath);
    }
}
