package com.cloudxplorer.springboot_todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cloudxplorer.springboot_todo.model.entity.Tasks;

// @Repository
public interface TasksRepository extends JpaRepository<Tasks, Integer> {
    boolean existsByName(String name);
}
