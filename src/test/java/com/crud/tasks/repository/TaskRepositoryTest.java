package com.crud.tasks.repository;


import com.crud.tasks.domain.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
/*
For educational purpose. @DataJpaTest: This annotation is used for JPA related tests.
It configures an in-memory embedded database, scans for @Entity classes,
and configures Spring Data JPA repositories.
TestEntityManager: Used for JPA entity related operations in tests.
It's provided by Spring Boot to work in harmony with @DataJpaTest.
 */
//@ActiveProfiles("test")
@DataJpaTest
class TaskRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private TaskRepository taskRepository;

//    @Test
//    public void findAll_RetrievesAllTasks() {
//        // given
//        Task task1 = new Task(1L, "Task1", "Desc1");
//        Task task2 = new Task(2L,"Task2", "Desc2");
//        entityManager.persist(task1);
//        entityManager.persist(task2);
//        entityManager.flush();
//
//        // when
//        List<Task> tasks = taskRepository.findAll();
//
//        // then
//        assertThat(tasks).hasSize(2).contains(task1, task2);
//    }

//    @Test
//    public void save_PersistsTaskSuccessfully() {
//        // given
//        Task task = new Task(1L, "Task", "Desc");
//
//        // when
//        Task savedTask = taskRepository.save(task);
//
//        // then
//        assertThat(entityManager.find(Task.class, savedTask.getId())).isEqualTo(savedTask);
//    }

//    @Test
//    public void findById_RetrievesCorrectTask() {
//        // given
//        Task task = new Task(1L, "Task", "Desc");
//        entityManager.persist(task);
//        entityManager.flush();
//
//        // when
//        Optional<Task> found = taskRepository.findById(task.getId());
//
//        // then
//        assertThat(found).contains(task);
//    }
//
//    @Test
//    public void delete_RemovesTaskSuccessfully() {
//        // given
//        Task task = new Task(1L, "Task", "Desc");
//        entityManager.persist(task);
//        entityManager.flush();
//
//        // when
//        taskRepository.delete(task);
//
//        // then
//        assertThat(taskRepository.findById(task.getId())).isEmpty();
//    }

}