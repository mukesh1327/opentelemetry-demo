# Spring boot Todo app with Postgres DB

In this demo a todo application is built using spring boot with postgresql

___Endpoints___

1. To create a task  
    Method: POST  
    Endpoint: /api/tasks/create  
<http://localhost:9001/api/tasks/create>  

```json
    {
        "name": "Test Task",
        "description": "This is a sample task",
        "status": "PENDING",
        "dueDate": "2025-02-19T00:00:00Z"
    }
```

2. To get all tasks  
    Method: GET  
    Endpoint: /api/tasks/read  
<http://localhost:9001/api/tasks/read>  

3. To get a task by ID  
    Method: GET  
    Endpoint: /api/tasks/get/{taskId}  
<http://localhost:9001/api/tasks/get/{taskId}>  

4. To update a task  
    Method: PUT  
    Endpoint: /api/tasks/update/{taskId}  
<http://localhost:9001/api/tasks/update/{taskId}>  

```json
    {
        "title": "Updated Task",
        "description": "Updated description",
        "status": "IN_PROGRESS"  // PENDING, IN_PROGRESS, DONE, KILLED 
    }
```

5. To delete a task  
    Method: DELETE  
    Endpoint: /api/tasks/delete/{taskId}  
<http://localhost:9001/api/tasks/delete/{taskId}>  
