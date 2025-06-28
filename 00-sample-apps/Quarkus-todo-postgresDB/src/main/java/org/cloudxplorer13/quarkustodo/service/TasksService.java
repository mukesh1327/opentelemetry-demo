package org.noventiqvaluepoint.quarkustodo.service;

import org.noventiqvaluepoint.quarkustodo.controller.wrapper.TaskResponse;
import org.noventiqvaluepoint.quarkustodo.model.dto.TasksDto;

import java.util.List;

public interface TasksService {
    TaskResponse createTask(TasksDto dto);
    List<TasksDto> getAllTasks();
    TaskResponse getTaskById(Long id);
    TasksDto updateTask(Long id, TasksDto dto);
    void deleteTask(Long id);
}
