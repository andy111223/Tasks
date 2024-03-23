package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import com.google.gson.Gson;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;


@SpringJUnitWebConfig
@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private DbService service;
    @MockBean
    private TaskMapper taskMapper;


    @Test
    void shouldGetTasks() throws Exception {
        //Arrange
        List<Task> taskList = List.of(new Task(1L,"Test title", "Test content"));
        List<TaskDto> taskDtoList = List.of(new TaskDto(1L,"Test title","Test content" ));
        when(service.getAllTasks()).thenReturn(taskList);
        when(taskMapper.mapToTaskDtoList(taskList)).thenReturn(taskDtoList);
        //Act & Assert
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title", Matchers.is("Test title")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].content", Matchers.is("Test content")));

    }
    @Test
    void shouldGetTasksEmptyList() throws Exception {
        //Arrange
        when(service.getAllTasks()).thenReturn(List.of());
        when(taskMapper.mapToTaskDtoList(anyList())).thenReturn(List.of());
        //Act & Assert
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(0)));
    }

    @Test
    void shouldGetTask() throws Exception {
        //Arrange
        Task task = new Task(1L,"Test title", "Test content");
        TaskDto taskDto = new TaskDto(1L,"Test title","Test content" );
        when(service.getTask(task.getId())).thenReturn(task);
        when(taskMapper.mapToTaskDto(task)).thenReturn(taskDto);
        //Act & Assert
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/tasks/{taskId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id",Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title",Matchers.is("Test title")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content",Matchers.is("Test content")));
    }
    @Test
    void shouldDeleteTask() throws Exception {
        //Arrange
        Task task = new Task(1L,"Test title", "Test content");
        when(service.getTask(task.getId())).thenReturn(task);
        //Act & Assert
        mockMvc
                .perform(MockMvcRequestBuilders
                        .delete("/v1/tasks/{taskId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void shouldUpdateTask() throws Exception {
        //Given
        TaskDto taskDto = new TaskDto(1L, "Task Dto test title", "Task Dto test content");
        Task task = new Task(1L, "Task test title", "Task test content");

        when(taskMapper.mapToTask(any(TaskDto.class))).thenReturn(task);
        when(service.saveTask(task)).thenReturn(task);
        when(taskMapper.mapToTaskDto(task)).thenReturn(taskDto);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is("Task Dto test title")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.is("Task Dto test content")));
    }
    @Test
    void shouldCreateTask() throws Exception {
        //Arrange
        TaskDto newTaskDto = new TaskDto(null, "Task Dto test title", "Task Dto test content");
        Task newTask = new Task(null, "Task test title", "Task test content");
        when(taskMapper.mapToTask(any(TaskDto.class))).thenReturn(newTask);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(newTaskDto);
        //Act & Assert
        mockMvc
                .perform(MockMvcRequestBuilders

                        .post("/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().isOk());
        verify(taskMapper).mapToTask(refEq(newTaskDto));
        verify(service).saveTask(refEq(newTask));
    }
}