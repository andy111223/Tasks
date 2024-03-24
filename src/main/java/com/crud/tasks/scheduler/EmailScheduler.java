package com.crud.tasks.scheduler;

import com.crud.tasks.domain.Mail;
import com.crud.tasks.repository.TaskRepository;
import com.crud.tasks.service.TasksSizeEmailService;
import com.crud.tasks.trello.config.AdminConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailScheduler {

    private static final String SUBJECT = "Tasks: Once a day email";

    private final TasksSizeEmailService tasksSizeEmailService;
    private final TaskRepository taskRepository;
    private final AdminConfig adminConfig;

    //@Scheduled(cron = "0 */1 * * * *")
    @Scheduled(cron = "0 0 10 * * *")
    public void sendInformationEmail() {
        long size = taskRepository.count();
        String message = createMessage(size);
        tasksSizeEmailService.send(
                new Mail(
                    adminConfig.getAdminMail(),
                    SUBJECT,
                    message,
                    null
        ));
    }

    private String createMessage(long size) {
        return "Currently in database you got: " + size + (size == 1 ? " task" : " tasks");
    }
}
