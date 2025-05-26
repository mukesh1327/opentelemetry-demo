package com.cloudxplorer.springboot_todo.service;

import java.util.List;

import com.cloudxplorer.springboot_todo.controller.wrapper.TaskResponse;
import com.cloudxplorer.springboot_todo.model.dto.TasksDto;
import com.cloudxplorer.springboot_todo.model.entity.Tasks;

public interface TasksService {
    TasksDto convertToDTO(Tasks task);
    TaskResponse createTask(TasksDto taskDTO);
    TaskResponse getTaskById(Integer taskId);
    TasksDto updateTask(Integer taskId, TasksDto taskDTO);
    void deleteTask(Integer taskId);
    List<TasksDto> getAllTasks();
}
