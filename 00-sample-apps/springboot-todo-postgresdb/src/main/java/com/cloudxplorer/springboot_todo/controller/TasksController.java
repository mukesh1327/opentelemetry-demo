package com.cloudxplorer.springboot_todo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cloudxplorer.springboot_todo.controller.wrapper.TaskResponse;
import com.cloudxplorer.springboot_todo.exception.TasksExceptions.TaskAlreadyExistsException;
import com.cloudxplorer.springboot_todo.exception.TasksExceptions.TaskNotFoundException;
import com.cloudxplorer.springboot_todo.exception.TasksExceptions.UnableToCreateTaskException;
import com.cloudxplorer.springboot_todo.model.dto.TasksDto;
import com.cloudxplorer.springboot_todo.service.TasksService;

@RestController
@RequestMapping("/api/tasks")
public class TasksController {
    private final TasksService tasksService;

    public TasksController(TasksService tasksService) {
        this.tasksService = tasksService;
    }

    // Create a task = /api/tasks/create
    @PostMapping("/create")
    public ResponseEntity<TaskResponse> createTask(@RequestBody TasksDto taskDTO) {
        try {
            TaskResponse createdTaskResponse = tasksService.createTask(taskDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdTaskResponse);
        } catch (UnableToCreateTaskException e) {
            TaskResponse errorResponse = new TaskResponse();
            errorResponse.setMessage("Unable to create task: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        } catch (TaskAlreadyExistsException e) {
            TaskResponse errorResponse = new TaskResponse();
            errorResponse.setMessage("Task already exists: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
        }
    }

    // Get all tasks = /api/tasks
    @GetMapping("/read")
    public ResponseEntity<List<TasksDto>> getAllTasks() {
        List<TasksDto> tasks = tasksService.getAllTasks();
        return ResponseEntity.ok(tasks);
    }

    // Get task by ID = /api/tasks/get/1
    @GetMapping("/get/{taskId}")
    public ResponseEntity<TaskResponse> getTaskById(@PathVariable Integer taskId) {

        try {
            TaskResponse taskResponse = tasksService.getTaskById(taskId);
            return ResponseEntity.ok(taskResponse);
        } catch (TaskNotFoundException e) {
            TaskResponse errorResponse = new TaskResponse();
            errorResponse.setMessage("Task not found: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }

        // TasksDto taskDTO = tasksService.getTaskById(taskId);
        // if (taskDTO != null) {
        //     return ResponseEntity.ok(taskDTO);
        // } else {
        //     return ResponseEntity.notFound().build();
        // }
    }

    // Update task by ID = /api/tasks/update/1
    @PutMapping("/update/{taskId}")
    public ResponseEntity<TasksDto> updateTask(
            @PathVariable Integer taskId,
            @RequestBody TasksDto taskDTO) {
        TasksDto updatedTask = tasksService.updateTask(taskId, taskDTO);
        if (updatedTask != null) {
            return ResponseEntity.ok(updatedTask);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete task by ID = /api/tasks/delete/1
    @DeleteMapping("/delete/{taskId}")
    public ResponseEntity<String> deleteTask(@PathVariable Integer taskId) {
        try {
            tasksService.deleteTask(taskId);
            return ResponseEntity.ok("Task with ID " + taskId + " deleted successfully");
        } catch (TaskNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task with ID " + taskId + " not found");
        }
    }

}
