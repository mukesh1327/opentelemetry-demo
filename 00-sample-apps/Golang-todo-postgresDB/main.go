package main

import (
    "golang-todo-postgresdb/config"
    "golang-todo-postgresdb/handlers"

    "github.com/gin-gonic/gin"
)

func main() {
    config.ConnectDatabase()

    r := gin.Default()

    r.POST("api/tasks/create", handlers.CreateTask)
    r.GET("api/tasks/read", handlers.GetAllTasks)
    r.GET("api/tasks/get/:id", handlers.GetTaskById)
    r.PUT("api/tasks/update/:id", handlers.UpdateTask)
    r.DELETE("api/tasks/delete/:id", handlers.DeleteTask)

    r.Run(":8080")
}
