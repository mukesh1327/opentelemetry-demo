package org.noventiqvaluepoint.quarkustodo.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import org.noventiqvaluepoint.quarkustodo.controller.wrapper.TaskResponse;
import org.noventiqvaluepoint.quarkustodo.exceptions.TasksExceptions.*;
import org.noventiqvaluepoint.quarkustodo.model.dto.TasksDto;
import org.noventiqvaluepoint.quarkustodo.model.entity.Tasks;
import org.noventiqvaluepoint.quarkustodo.model.entity.TasksStatusEnum;
import org.noventiqvaluepoint.quarkustodo.repository.TasksRepository;


import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class TasksServiceImpl implements TasksService {

    private final TasksRepository repository;

    public TasksServiceImpl(TasksRepository repository) {
        this.repository = repository;
    }

    private TasksDto toDto(Tasks task) {
        TasksDto dto = new TasksDto();
        dto.setId(task.getId());
        dto.setName(task.getName());
        dto.setDescription(task.getDescription());
        dto.setStatus(task.getStatus());
        dto.setDueDate(task.getDueDate());
        return dto;
    }

    @Override
    @Transactional
    public TaskResponse createTask(TasksDto dto) {
        if (repository.existsByName(dto.getName())) {
            throw new TaskAlreadyExistsException("Task with name '" + dto.getName() + "' already exists.");
        }

        Tasks task = new Tasks();
        task.setName(dto.getName());
        task.setDescription(dto.getDescription());
        task.setDueDate(dto.getDueDate());
        task.setStatus(dto.getStatus() != null ? dto.getStatus() : TasksStatusEnum.PENDING);

        try {
            repository.persist(task);
            TaskResponse res = new TaskResponse();
            res.setMessage("Task created successfully");
            res.setTaskDto(toDto(task));
            return res;
        } catch (Exception e) {
            throw new UnableToCreateTaskException(e.getMessage());
        }
    }

    @Override
    public List<TasksDto> getAllTasks() {
        return repository.listAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public TaskResponse getTaskById(Long id) {
        Tasks task = repository.findByIdOptional(id).orElseThrow(() -> new TaskNotFoundException("Task not found with ID: " + id));
        TaskResponse res = new TaskResponse();
        res.setMessage("Task found");
        res.setTaskDto(toDto(task));
        return res;
    }

    @Override
    @Transactional
    public TasksDto updateTask(Long id, TasksDto dto) {
        Tasks task = repository.findByIdOptional(id).orElseThrow(() -> new TaskNotFoundException("Task not found with ID: " + id));

        task.setName(dto.getName());
        task.setDescription(dto.getDescription());
        task.setStatus(dto.getStatus());
        task.setDueDate(dto.getDueDate());
        return toDto(task);
    }

    @Override
    @Transactional
    public void deleteTask(Long id) {
        Tasks task = repository.findByIdOptional(id).orElseThrow(() -> new TaskNotFoundException("Task not found with ID: " + id));
        repository.delete(task);
    }
}
