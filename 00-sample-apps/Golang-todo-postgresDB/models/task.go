package models

import (
    "time"
)

type TasksStatusEnum string

const (
    Pending    TasksStatusEnum = "PENDING"
    InProgress TasksStatusEnum = "IN_PROGRESS"
    Completed  TasksStatusEnum = "COMPLETED"
)

type Task struct {
    ID          uint            `gorm:"primaryKey" json:"id"`
    Name        string          `gorm:"unique;not null" json:"name"`
    Description string          `json:"description"`
    Status      TasksStatusEnum `gorm:"type:varchar(20)" json:"status"`
    DueDate     *time.Time      `json:"dueDate"`
    CreatedAt   time.Time
    UpdatedAt   time.Time
}
