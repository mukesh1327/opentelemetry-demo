package com.cloudxplorer13.springboot_todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cloudxplorer13.springboot_todo.model.entity.Tasks;

// @Repository
public interface TasksRepository extends JpaRepository<Tasks, Integer> {
    boolean existsByName(String name);
}
