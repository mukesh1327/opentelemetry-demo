package org.cloudxplorer13.quarkustodo.model.dto;

import org.cloudxplorer13.quarkustodo.model.entity.TasksStatusEnum;

import java.time.LocalDate;

public class TasksDto {
    private Long id;
    private String name;
    private String description;
    private TasksStatusEnum status;
    private LocalDate dueDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TasksStatusEnum getStatus() {
        return status;
    }

    public void setStatus(TasksStatusEnum status) {
        this.status = status;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
}
