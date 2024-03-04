package com.crud.tasks.scheduler;

import com.crud.tasks.repository.TaskRepository;
import com.crud.tasks.service.SimpleEmailService;
import com.crud.tasks.trello.config.AdminConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmailSchedulerTest {

    @InjectMocks
    private EmailScheduler emailScheduler;
    @Mock
    private SimpleEmailService simpleEmailService;
    @Mock
    private TaskRepository taskRepository;
    @Mock
    private AdminConfig adminConfig;

    @Test
    void shouldSendInformationEmail() {
        //Arrange
        when(adminConfig.getAdminMail()).thenReturn("example@kulfon.net");
        when(taskRepository.count()).thenReturn(1L);
        //Act
        emailScheduler.sendInformationEmail();
        //Assert
        verify(simpleEmailService).send(argThat(mail ->
                mail.getMailTo().equals("example@kulfon.net") &&
                mail.getSubject().equals("Tasks: Once a day email") &&
                mail.getMessage().equals("Currently in database you got: 1 task")
        ));
    }
}