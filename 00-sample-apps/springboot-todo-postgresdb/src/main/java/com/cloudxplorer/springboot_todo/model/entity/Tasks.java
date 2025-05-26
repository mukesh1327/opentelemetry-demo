package com.cloudxplorer.springboot_todo.model.entity;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
// import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Table(name = "tasks")
@Entity
public class Tasks {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer id;

    @Column(unique = true, length = 200, nullable = false)
    private String name;

    @Lob
    private String description;

    // @Column(nullable = false, columnDefinition = "varchar(10) default 'PENDING' not null")
    @Column(nullable = false, columnDefinition = "varchar(20) default 'PENDING'")
    @Enumerated(EnumType.STRING)
    private TasksStatusEnum status;

    @Column(name = "due_date")
    private Date dueDate;

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;    
}
