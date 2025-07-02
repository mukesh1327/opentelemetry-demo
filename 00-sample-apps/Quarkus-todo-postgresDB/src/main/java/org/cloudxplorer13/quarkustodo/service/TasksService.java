package org.cloudxplorer13.quarkustodo.service;

import org.cloudxplorer13.quarkustodo.controller.wrapper.TaskResponse;
import org.cloudxplorer13.quarkustodo.model.dto.TasksDto;

import java.util.List;

public interface TasksService {
    TaskResponse createTask(TasksDto dto);
    List<TasksDto> getAllTasks();
    TaskResponse getTaskById(Long id);
    TasksDto updateTask(Long id, TasksDto dto);
    void deleteTask(Long id);
}
