package com.crud.tasks.service;

import com.crud.tasks.controller.TaskNotFoundException;
import com.crud.tasks.domain.Task;
import com.crud.tasks.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DbServiceTest {

    @Mock
    private TaskRepository repository;
    @InjectMocks
    private DbService dbService;

    @Test
    void getAllTasks() {
        // Arrange
        Task expectedTask = new Task(1L, "Test name", "Test content");
        when(repository.findAll()).thenReturn(List.of(expectedTask));

        // Act
        List<Task> fetchedTasks = dbService.getAllTasks();

        // Assert
        assertThat(fetchedTasks).isNotEmpty();
        assertThat(fetchedTasks).hasSize(1);
        Task resultTask = fetchedTasks.get(0);
        assertThat(resultTask.getId()).isEqualTo(expectedTask.getId());
        assertThat(resultTask.getTitle()).isEqualTo(expectedTask.getTitle());
        assertThat(resultTask.getContent()).isEqualTo(expectedTask.getContent());
    }

    @Test
    void saveTask() {
        //Arrange
        Task newTask = new Task(1L, "Test name", "Test content");
        when(repository.save(newTask)).thenReturn(newTask);
        //Act
        Task savedTask = dbService.saveTask(newTask);
        //Assert
        assertThat(savedTask).isNotNull();
        assertThat(savedTask).usingRecursiveComparison().isEqualTo(newTask);
    }
    @Test
    void getTaskWhenExists() throws TaskNotFoundException {
        // Arrange
        Task expectedTask = new Task(1L, "Test Task", "Description");
        when(repository.findById(1L)).thenReturn(Optional.of(expectedTask));

        // Act
        Task result = dbService.getTask(1L);

        // Assert
        assertThat(result).isEqualTo(expectedTask);
    }

    @Test
    void getTaskWhenNotExists() {
        // Arrange
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        // Assert
        assertThatThrownBy(() -> dbService.getTask(1L))
                .isInstanceOf(TaskNotFoundException.class);
    }

    @Test
    void deleteTaskWhenExists() {
        //Arrange
        Task oldTask = new Task(1L, "Test Task", "Description");
        //Act
        dbService.deleteTask(oldTask);
        //Assert
        verify(repository).delete(oldTask);
    }
    @Test
    void deleteTaskWhenNotExists() {
        //Arrange
        Task nonExistentTask = new Task(99L, "Non-existent Task", "No Content");
        // Assuming an exception is thrown when attempting to delete a non-existent task
        doThrow(new EmptyResultDataAccessException(1)).when(repository).delete(nonExistentTask);
        // Assert
        assertThrows(EmptyResultDataAccessException.class, () -> dbService.deleteTask(nonExistentTask));
        // Verify that delete was attempted with the non-existent Task object
        verify(repository).delete(nonExistentTask);
    }
}