package com.crud.tasks.service;

import com.crud.tasks.trello.config.AdminConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.ArrayList;
import java.util.List;

@Service
public class MailCreatorService {

    @Autowired
    @Qualifier("templateEngine")
    public TemplateEngine templateEngine;

    @Autowired
    private AdminConfig adminConfig;

    Context context = new Context();

    public String buildTrelloCardEmail(String message) {

        List<String> functionality = new ArrayList<>();
        functionality.add("You can manage your tasks");
        functionality.add("Provides connection with Trello Account");
        functionality.add("Application allows sending tasks to Trello");

        context.setVariable("message", message);
        context.setVariable("tasks_url", "http://localhost:8888/tasks_frontend/");
        context.setVariable("button", "Visit website");
        context.setVariable("admin_config", adminConfig);
        context.setVariable("company_name",adminConfig.getCompanyName());
        context.setVariable("company_goal",adminConfig.getCompanyGoal());
        context.setVariable("company_email",adminConfig.getCompanyEmail());
        context.setVariable("company_phone",adminConfig.getCompnayPhone());
        context.setVariable("show_button", false);
        context.setVariable("is_friend", false);
        context.setVariable("application_functionality", functionality);

        return templateEngine.process("mail/created-trello-card-mail", context);
    }

    public String numberOfTasksEmail(String message) {
        List<String> functionality = new ArrayList<>();
        functionality.add("Efficiently manage your tasks!");
        functionality.add("Feel free to add more tasks");
        functionality.add("Clear your mind and delete some tasks");

        context.setVariable("message", message);
        context.setVariable("tasks_url", "http://localhost:8888/tasks_frontend/");
        context.setVariable("button", "Visit website");
        context.setVariable("admin_config", adminConfig);
        context.setVariable("company_name",adminConfig.getCompanyName());
        context.setVariable("company_goal",adminConfig.getCompanyGoal());
        context.setVariable("company_email",adminConfig.getCompanyEmail());
        context.setVariable("company_phone",adminConfig.getCompnayPhone());
        context.setVariable("show_button", false);
        context.setVariable("is_friend", false);
        context.setVariable("application_functionality", functionality);

        return templateEngine.process("mail/number-of-tasks", context);
    }
}
