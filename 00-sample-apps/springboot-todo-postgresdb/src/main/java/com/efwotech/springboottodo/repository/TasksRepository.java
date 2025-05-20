package com.efwotech.springboottodo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efwotech.springboottodo.model.entity.Tasks;

// @Repository
public interface TasksRepository extends JpaRepository<Tasks, Integer> {
    boolean existsByName(String name);
}
