package org.noventiqvaluepoint.quarkustodo.controller;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.noventiqvaluepoint.quarkustodo.controller.wrapper.TaskResponse;
import org.noventiqvaluepoint.quarkustodo.model.dto.TasksDto;
import org.noventiqvaluepoint.quarkustodo.service.TasksService;
import org.noventiqvaluepoint.quarkustodo.exceptions.TasksExceptions.*;

@Path("/api/tasks")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TasksController {

    @Inject
    TasksService tasksService;

    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createTask(TasksDto dto) {
        try {
            return Response.status(Response.Status.CREATED).entity(tasksService.createTask(dto)).build();
        } catch (TaskAlreadyExistsException e) {
            return Response.status(Response.Status.CONFLICT).entity(new TaskResponse("Task already exists: " + e.getMessage(), null)).build();
        } catch (UnableToCreateTaskException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new TaskResponse("Unable to create task: " + e.getMessage(), null)).build();
        }
    }

    @GET
    @Path("/read")
    public Response getAllTasks() {
        return Response.ok(tasksService.getAllTasks()).build();
    }

    @GET
    @Path("/get/{id}")
    public Response getTaskById(@PathParam("id") Long id) {
        try {
            return Response.ok(tasksService.getTaskById(id)).build();
        } catch (TaskNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(new TaskResponse("Task not found: " + e.getMessage(), null)).build();
        }
    }

    @PUT
    @Path("/update/{id}")
    public Response updateTask(@PathParam("id") Long id, TasksDto dto) {
        try {
            return Response.ok(tasksService.updateTask(id, dto)).build();
        } catch (TaskNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity("Task not found with ID: " + id).build();
        }
    }

    @DELETE
    @Path("/delete/{id}")
    public Response deleteTask(@PathParam("id") Long id) {
        try {
            tasksService.deleteTask(id);
            return Response.ok("Task with ID " + id + " deleted successfully").build();
        } catch (TaskNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity("Task with ID " + id + " not found").build();
        }
    }
}
