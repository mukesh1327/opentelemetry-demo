package handlers

import (
    "net/http"
    "golang-todo-postgresdb/config"
    "golang-todo-postgresdb/models"

    "github.com/gin-gonic/gin"
)

func CreateTask(c *gin.Context) {
    var input models.Task
    if err := c.ShouldBindJSON(&input); err != nil {
        c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
        return
    }

    if input.Status == "" {
        input.Status = models.Pending
    }

    task := models.Task{
        Name:        input.Name,
        Description: input.Description,
        Status:      input.Status,
        DueDate:     input.DueDate,
    }

    if err := config.DB.Create(&task).Error; err != nil {
        c.JSON(http.StatusInternalServerError, gin.H{"error": err.Error()})
        return
    }

    c.JSON(http.StatusOK, task)
}

func GetAllTasks(c *gin.Context) {
    var tasks []models.Task
    config.DB.Find(&tasks)
    c.JSON(http.StatusOK, tasks)
}

func GetTaskById(c *gin.Context) {
    var task models.Task
    id := c.Param("id")

    if err := config.DB.First(&task, id).Error; err != nil {
        c.JSON(http.StatusNotFound, gin.H{"error": "Task not found"})
        return
    }

    c.JSON(http.StatusOK, task)
}

func UpdateTask(c *gin.Context) {
    var task models.Task
    id := c.Param("id")

    if err := config.DB.First(&task, id).Error; err != nil {
        c.JSON(http.StatusNotFound, gin.H{"error": "Task not found"})
        return
    }

    var input models.Task
    if err := c.ShouldBindJSON(&input); err != nil {
        c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
        return
    }

    updated := models.Task{
        Name:        input.Name,
        Description: input.Description,
        Status:      input.Status,
        DueDate:     input.DueDate,
    }

    config.DB.Model(&task).Updates(updated)
    c.JSON(http.StatusOK, task)
}

func DeleteTask(c *gin.Context) {
    var task models.Task
    id := c.Param("id")

    if err := config.DB.First(&task, id).Error; err != nil {
        c.JSON(http.StatusNotFound, gin.H{"error": "Task not found"})
        return
    }

    config.DB.Delete(&task)
    c.JSON(http.StatusOK, gin.H{"message": "Task deleted successfully"})
}
