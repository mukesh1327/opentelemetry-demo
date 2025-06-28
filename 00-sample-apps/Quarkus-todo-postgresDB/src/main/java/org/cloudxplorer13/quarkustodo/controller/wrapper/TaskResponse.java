package org.noventiqvaluepoint.quarkustodo.controller.wrapper;

import org.noventiqvaluepoint.quarkustodo.model.dto.TasksDto;


public class TaskResponse {
    private String message;
    private TasksDto taskDto;

    public TaskResponse(String message, TasksDto taskDto) {
        this.message = message;
        this.taskDto = taskDto;
    }
    public TaskResponse() {}
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public TasksDto getTaskDto() {
        return taskDto;
    }

    public void setTaskDto(TasksDto taskDto) {
        this.taskDto = taskDto;
    }
}
