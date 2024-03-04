package com.crud.tasks.mapper;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;

class TaskMapperTest {

    @Test
    void mapToTask() {
        //Arrange
        TaskMapper taskMapper = new TaskMapper();
        TaskDto taskDto = new TaskDto(1L,"Test task", "Test content");
        //Act
        Task mappedTask = taskMapper.mapToTask(taskDto);
        //Assert
        assertNotNull(mappedTask);
        assertThat(mappedTask.getId()).isEqualTo(taskDto.getId());
        assertThat(mappedTask.getTitle()).isEqualTo(taskDto.getTitle());
        assertThat(mappedTask.getContent()).isEqualTo(taskDto.getContent());
    }

    @Test
    void mapToTaskDto() {
        //Arrange
        TaskMapper taskMapper = new TaskMapper();
        Task task = new Task(1L,"Test task", "Test content");
        //Act
        TaskDto mappedTaskDto = taskMapper.mapToTaskDto(task);
        //Assert
        assertNotNull(mappedTaskDto);
        assertThat(mappedTaskDto.getId()).isEqualTo(task.getId());
        assertThat(mappedTaskDto.getTitle()).isEqualTo(task.getTitle());
        assertThat(mappedTaskDto.getContent()).isEqualTo(task.getContent());
    }

    @Test
    void mapToOptionalPresentTaskDto() {
        //Arrange
        TaskMapper taskMapper = new TaskMapper();
        Task task = new Task(1L,"Test task", "Test content");
        Optional<Task> optionalTask = Optional.of(task);
        //Act
        TaskDto result = taskMapper.mapToTaskDto(optionalTask);
        //Assert
        assertNotNull(result);
        assertNotNull(result);
        assertEquals(task.getId(), result.getId());
        assertEquals(task.getTitle(), result.getTitle());
        assertEquals(task.getContent(), result.getContent());
    }
    @Test
    void mapToOptionalEmptyTaskDto() {
        //Arrange
        TaskMapper taskMapper = new TaskMapper();
        Optional<Task> optionalTask = Optional.empty();
        //Assert
        assertThrows(NoSuchElementException.class, () -> {
            //Act
            taskMapper.mapToTaskDto(optionalTask);
        });
    }


    @Test
    void mapToTaskDtoList() {
        //Arrange
        TaskMapper taskMapper = new TaskMapper();
        List<Task> inputList = List.of(new Task(1L,"Test task", "Test content"));
        //Act
        List<TaskDto> mappedTaskDtoList = taskMapper.mapToTaskDtoList(inputList);
        //Assert
        assertNotNull(mappedTaskDtoList);
        assertEquals(inputList.size(),mappedTaskDtoList.size());
        Task sourceTask = inputList.get(0);
        TaskDto mappedTaskDto = mappedTaskDtoList.get(0);
        assertAll("Should correctly map the list object properties",
                () -> assertEquals(sourceTask.getId(),mappedTaskDto.getId()),
                () -> assertEquals(sourceTask.getTitle(),mappedTaskDto.getTitle()),
                () -> assertEquals(sourceTask.getContent(),mappedTaskDto.getContent())
        );
    }
}