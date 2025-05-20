package com.efwotech.springboottodo.service;

import java.util.List;

import com.efwotech.springboottodo.controller.wrapper.TaskResponse;
import com.efwotech.springboottodo.model.dto.TasksDto;
import com.efwotech.springboottodo.model.entity.Tasks;

public interface TasksService {
    TasksDto convertToDTO(Tasks task);
    TaskResponse createTask(TasksDto taskDTO);
    TaskResponse getTaskById(Integer taskId);
    TasksDto updateTask(Integer taskId, TasksDto taskDTO);
    void deleteTask(Integer taskId);
    List<TasksDto> getAllTasks();
}
