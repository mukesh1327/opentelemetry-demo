package com.cloudxplorer.springboot_todo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.cloudxplorer.springboot_todo.controller.wrapper.TaskResponse;
import com.cloudxplorer.springboot_todo.exception.TasksExceptions.TaskAlreadyExistsException;
import com.cloudxplorer.springboot_todo.exception.TasksExceptions.TaskNotFoundException;
import com.cloudxplorer.springboot_todo.exception.TasksExceptions.UnableToCreateTaskException;
import com.cloudxplorer.springboot_todo.model.dto.TasksDto;
import com.cloudxplorer.springboot_todo.model.entity.Tasks;
import com.cloudxplorer.springboot_todo.model.entity.TasksStatusEnum;
import com.cloudxplorer.springboot_todo.repository.TasksRepository;

@Service
public class TasksServiceImpl implements TasksService {

    private final Logger logger = LoggerFactory.getLogger(TasksServiceImpl.class);
    private final ModelMapper modelMapper;
    private final TasksRepository tasksRepository;

    public TasksServiceImpl(ModelMapper modelMapper, TasksRepository tasksRepository) {
        this.modelMapper = modelMapper;
        this.tasksRepository = tasksRepository;
    }

    @Override
    public TasksDto convertToDTO(Tasks task) {
        return modelMapper.map(task, TasksDto.class);
    }

    // Create Task
    @Override
    public TaskResponse createTask(TasksDto tasksDTO) {
        String taskName = tasksDTO.getName();
        if (tasksRepository.existsByName(taskName)) {
            throw new TaskAlreadyExistsException("Task with name '" + taskName + "' already exists.");
        }
    
        Tasks task = modelMapper.map(tasksDTO, Tasks.class);
        task.setId(null); // << IMPORTANT: Always set ID to null when creating new
    
        if (tasksDTO.getStatus() == null) {
            task.setStatus(TasksStatusEnum.PENDING);
        }
    
        try {
            logger.info("Creating new task");
            Tasks createdTask = tasksRepository.save(task);
            logger.info("Created new task");
            TasksDto createdTaskDto = modelMapper.map(createdTask, TasksDto.class);
            TaskResponse response = new TaskResponse();
            response.setTaskDto(createdTaskDto);
            response.setMessage("Task created successfully");
            return response;
        } catch (Exception e) {
            logger.warn("Task not created");
            throw new UnableToCreateTaskException("Unable to create task: " + e.getMessage());
        }
    }

    // Get all tasks
    @Override
    public List<TasksDto> getAllTasks() {
    logger.info("listing all tasks...");
    Iterable<Tasks> tasksIterable = tasksRepository.findAll();
    List<Tasks> tasksList = new ArrayList<>();
    tasksIterable.forEach(tasksList::add);

    List<TasksDto> taskDTOList = tasksList.stream()
            .map(task -> modelMapper.map(task, TasksDto.class))
            .collect(Collectors.toList());
    logger.info("All tasks are listed");
    return taskDTOList;
    }

    // Get task by ID
    @Override
    public TaskResponse getTaskById(Integer taskId) {
        logger.info("Getting task with ID: {}", taskId);
        Optional<Tasks> optionalTask = tasksRepository.findById(taskId);
        if (optionalTask.isPresent()) {
            TasksDto taskDto = modelMapper.map(optionalTask.get(), TasksDto.class);
            TaskResponse response = new TaskResponse();
            response.setTaskDto(taskDto);
            response.setMessage("Task found");
            logger.info("Task with ID:"+ taskId + "found");
            return response;
        } else {
            logger.warn("Task with ID" + taskId + "not found");
            throw new TaskNotFoundException("Task not found with ID: " + taskId); // Task with given ID not found
        }    
    }

    // Update task by ID
    @Override
    public TasksDto updateTask(Integer taskId, TasksDto taskDTO) {
        logger.info("Updating task with ID: {}", taskId);
        Optional<Tasks> optionalTask = tasksRepository.findById(taskId);
        if (optionalTask.isPresent()) {
            Tasks existingTask = optionalTask.get();
            existingTask.setName(taskDTO.getName());
            existingTask.setDescription(taskDTO.getDescription());
            existingTask.setStatus(taskDTO.getStatus());
            existingTask.setDueDate(taskDTO.getDueDate());
            Tasks updatedTask = tasksRepository.save(existingTask);
            logger.info("Task with ID {} updated successfully", taskId);
            return modelMapper.map(updatedTask, TasksDto.class);
        } else {
            logger.warn("Task with ID {} not found, update operation skipped", taskId);
            throw new TaskNotFoundException("Task not found with ID: " + taskId); // Task with given ID not found
        }
    }

    // Delete task by ID
    @Override
    public void deleteTask(Integer taskId) {
        Optional<Tasks> optionalTask = tasksRepository.findById(taskId);
        if (optionalTask.isPresent()) {
            logger.info("Deleting task with ID: {}", taskId);
            tasksRepository.delete(optionalTask.get());
            logger.info("Task with ID {} deleted successfully", taskId);
        } else {
            logger.warn("Task with ID {} not found, delete operation skipped", taskId);
            throw new TaskNotFoundException("Task not found with ID: " + taskId);
        }
    }

}
