package com.example.task_manager_ui.controller;

import com.example.task_manager_ui.model.TaskDto;
import com.example.task_manager_ui.service.TaskService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String saveTask(@Valid @ModelAttribute(name = "task") TaskDto taskDto,
                           BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "task-form";
        }
        TaskDto savedTask = taskService.saveTask(taskDto);
        redirectAttributes.addFlashAttribute("saveMessage",
                "The task has been successfully saved!");

        return "redirect:/dashboard";
    }

    @GetMapping("/{id}/complete")
    public String completeTask(@PathVariable long id, HttpSession session, RedirectAttributes redirectAttributes) {
        String previousPath = session.getAttribute("previousPath").toString();

        taskService.changeStatusToComplete(id);
        redirectAttributes.addFlashAttribute("completeMessage",
                "You have successfully completed the task!");

        return "redirect:%s".formatted(previousPath);
    }

    @GetMapping("/{id}/modify")
    public String modifyTask(@PathVariable long id, Model model) {
        TaskDto task = taskService.findTaskById(id);
        model.addAttribute("task", task);

        return "task-form";
    }

    @GetMapping("/{id}/delete")
    public String deleteTask(@PathVariable long id, HttpSession session, RedirectAttributes redirectAttributes) {
        String previousPath = session.getAttribute("previousPath").toString();

        taskService.deleteTaskById(id);
        redirectAttributes.addFlashAttribute("deleteMessage",
                "You have successfully deleted the task!");

        return "redirect:%s".formatted(previousPath);
    }
}
